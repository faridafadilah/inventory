package com.inventory.backend.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.ProfileRequest;
import com.inventory.backend.server.dto.response.DtoUserRole;
import com.inventory.backend.server.dto.response.UserResponse;
import com.inventory.backend.server.repository.UserRepository;
import com.inventory.backend.server.services.ProfileService;

@CrossOrigin("*")
@RestController
public class ProfileController {
  @Autowired
  private ProfileService service;

  @Autowired
  private UserRepository repository;
  // router.get("/check-auth", auth, morganMiddleware, checkAuth);
  // router.delete("/delete/:id", auth, morganMiddleware, deleteUser);

  @GetMapping("/getUser/{id}")
  public ResponseEntity<ResponAPI<UserResponse>> getUserById(@PathVariable("id") Long id) {
    ResponAPI<UserResponse> responAPI = new ResponAPI<>();
    if (!service.getUserById(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @PostMapping("/updateProfile/{id}")
  public ResponseEntity<ResponAPI<UserResponse>> updateProfile(@PathVariable("id") Long id,
      @ModelAttribute ProfileRequest req, @RequestParam(value = "file", required = false) MultipartFile file,
      @RequestParam(value = "sampul", required = false) MultipartFile sampul) {
    ResponAPI<UserResponse> responAPI = new ResponAPI<>();
    if (!service.updateProfileById(id, req, file, responAPI, sampul)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  // Get All User Role
  @GetMapping("/userRole/")
  public ResponseEntity<ResponAPI<List<DtoUserRole>>> getUserRole() {
    ResponAPI<List<DtoUserRole>> responAPI = new ResponAPI<>();
    List<DtoUserRole> data = repository.getAllUserRole();
    responAPI.setData(data);
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.ok(responAPI);
  }

  //update role
  @PostMapping("/userRole/{roleId}/{userId}")
  public void updateRole(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) {
    service.updateRole(roleId, userId);
  }

  //UserROle by id
  @GetMapping("/userRole/role/{userId}")
  public ResponseEntity<ResponAPI<DtoUserRole>> getUserRoleById(@PathVariable("userId") Long userId) {
    ResponAPI<DtoUserRole> responAPI = new ResponAPI<>();
    DtoUserRole data = repository.getUserRole(userId);
    responAPI.setData(data);
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.ok(responAPI);
  }
}
