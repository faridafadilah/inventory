package com.inventory.backend.server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.dto.request.AdminRequest;
import com.inventory.backend.server.dto.request.LoginRequest;
import com.inventory.backend.server.dto.response.DtoResResetPassword;
import com.inventory.backend.server.dto.response.JwtResponse;
import com.inventory.backend.server.dto.response.MessageResponse;
import com.inventory.backend.server.model.ERole;
import com.inventory.backend.server.model.Role;
import com.inventory.backend.server.model.User;
import com.inventory.backend.server.repository.RoleRepository;
import com.inventory.backend.server.repository.UserRepository;
import com.inventory.backend.server.security.jwt.JwtUtils;
import com.inventory.backend.server.security.services.UserDetailslmpl;
import com.inventory.backend.server.services.AuthService;

@CrossOrigin("*")
@RestController
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  AuthService service;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailslmpl userDetails = (UserDetailslmpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(userDetails);
    System.out.println("jwt" + jwt);

    List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
        .collect(Collectors.toList());
    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(),
        userDetails.getUsername(), userDetails.getEmail(), roles));
  }

  // Controller SignUp
  @PostMapping("/addAdmin")
  public ResponseEntity<?> addAdmin(@Valid @RequestBody AdminRequest signUpRequest) {
    // Periksa Username atau Email
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user account
    User user = new User(signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "super":
            Role superRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(superRole);
            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/reset-password")
  public ResponseEntity<ResponAPI<String>> resetPassword(@RequestBody Map<String, Object> request) {
    ResponAPI<String> responseMessage = new ResponAPI<>();
    if(!service.resetPassword(responseMessage, request.get("token").toString(), request.get("password").toString())) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    }
    responseMessage.setErrorCode(ErrorCode.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ResponAPI<DtoResResetPassword>> forgotPassword(@RequestBody Map<String, Object> req) {
    ResponAPI<DtoResResetPassword> responseMessage = new ResponAPI<>();
    if(!service.forgetPassword(responseMessage, req.get("email").toString(), "http://localhost:8080/")) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseMessage);
    }
    responseMessage.setErrorCode(ErrorCode.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
  }

}
