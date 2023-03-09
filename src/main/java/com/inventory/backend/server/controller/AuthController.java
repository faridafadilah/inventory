package com.inventory.backend.server.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.server.dto.request.AdminRequest;
import com.inventory.backend.server.dto.response.MessageResponse;
import com.inventory.backend.server.model.ERole;
import com.inventory.backend.server.model.Role;
import com.inventory.backend.server.model.User;
import com.inventory.backend.server.repository.RoleRepository;
import com.inventory.backend.server.repository.UserRepository;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AuthController {
  // @Autowired
  // AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  // @Autowired
  // PasswordEncoder encoder;

  // @Autowired
  // JwtUtils jwtUtils;

  // @Autowired
  // RefreshTokenService refreshTokenService;

  // // @Autowired
  // // UserServices userService;

  // @PostMapping("/signin")
  // public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

  //   Authentication authentication = authenticationManager
  //       .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

  //   SecurityContextHolder.getContext().setAuthentication(authentication);

  //   UserDetailslmpl userDetails = (UserDetailslmpl) authentication.getPrincipal();

  //   String jwt = jwtUtils.generateJwtToken(userDetails);
  //   System.out.println("jwt" + jwt);

  //   List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
  //       .collect(Collectors.toList());

  //   RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), jwt);

  //   return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
  //       userDetails.getUsername(), userDetails.getEmail(), roles));
  // }

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
        signUpRequest.getPassword());

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

  // @PostMapping("/refreshtoken")
  // public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
  //   String requestRefreshToken = request.getRefreshToken();
  //   System.out.println("token sebelumnya" + requestRefreshToken);

  //   return refreshTokenService.findByToken(requestRefreshToken)
  //       .map(refreshTokenService::verifyExpiration)
  //       .map(RefreshToken::getUser)
  //       .map(user -> {
  //         String token = jwtUtils.generateTokenFromUsername(user.getUsername());
  //         System.out.println("token" + token);
  //         refreshTokenService.editRefreshToken(token, requestRefreshToken);
  //         return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
  //       })
  //       .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
  //           "Refresh token is not in database!"));
  // }

  // @PostMapping("/signout")
  // public ResponseEntity<?> logoutUser() {
  //   // Insert Table logout true
  //   // created_at dan expired_at
  //   UserDetailslmpl userDetails = (UserDetailslmpl) SecurityContextHolder.getContext().getAuthentication()
  //       .getPrincipal();
  //   Long userId = userDetails.getId();
  //   String username = userDetails.getUsername();
  //   refreshTokenService.createUserLogout(username, userId);
  //   refreshTokenService.deleteByUserId(userId);
  //   return ResponseEntity.ok(new MessageResponse("Log Out Successfull!"));
  // }

  // @PostMapping("/reset-password")
  // public ResponseEntity<String> resetPassword(@RequestParam String email) {
  //   User user = userService.getUserByEmail(email);
  //   if (user == null) {
  //     return ResponseEntity.badRequest().body("User not found.");
  //   }
  //   String token = UUID.randomUUID().toString();
  //   userService.createPasswordResetTokenForUser(user, token);
  //   userService.sendResetPasswordEmail(user, token);
  //   return ResponseEntity.ok("Password reset email sent.");
  // }

  // @PostMapping("/reset-password-confirm")
  // public ResponseEntity<String> resetPasswordConfirm(@RequestParam String token, @RequestParam String password) {
  //   PasswordResetToken passwordResetToken = userService.getPasswordResetToken(token);
  //   if (passwordResetToken == null) {
  //     return ResponseEntity.badRequest().body("Invalid token.");
  //   }
  //   User user = passwordResetToken.getUser();
  //   userService.changeUserPassword(user, password);
  //   return ResponseEntity.ok("Password reset successful.");
  // }

}
