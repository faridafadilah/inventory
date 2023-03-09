package com.inventory.backend.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.ErrorCodeApi;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.ProfileRequest;
import com.inventory.backend.server.dto.response.UserResponse;
import com.inventory.backend.server.repository.*;
import com.inventory.backend.server.model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ProfileService {
  private final Path root = Paths.get("./imageUser");
  private final Path roots = Paths.get("./imageSampul");
  @Autowired
  private UserRepository repository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public boolean getUserById(ResponAPI<UserResponse> responAPI, Long id) {
    Optional<User> optionalUser = repository.findById(id);
    if (!optionalUser.isPresent()) {
      responAPI.setErrorMessage("Data tidak ditemukan!");
      return false;
    }
    try {
      UserResponse response = UserResponse.getInstance(optionalUser.get());
      responAPI.setData(response);
    } catch (Exception e) {
      responAPI.setErrorMessage(e.getMessage());
    }
    return true;
  }

  public boolean updateProfileById(Long id, ProfileRequest req, MultipartFile file, ResponAPI<UserResponse> responAPI, MultipartFile sampul) {
    Optional<User> uOptional = repository.findById(id);
    if (!uOptional.isPresent()) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(MessageApi.BODY_NOT_VALID);
      return false;
    }

    try {
      User user = uOptional.get();
      String userImage = user.getImageProfile();
      String sampulImage = user.getSampulImage();
      user.setId(id);
      user.setAddress(req.getAddress());
      user.setDescription(req.getDescription());
      user.setPhone(req.getPhone());
      user.setEmail(req.getEmail());
      user.setName(req.getName());
      user.setGender(req.getGender());
      user.setPossition(req.getPossition());

      if (file != null && !file.isEmpty()) {
        if (userImage != null) {
          Path oldFile = root.resolve(userImage);
          Files.deleteIfExists(oldFile);
        }
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID().toString() + ext;
        Path filePath = this.root.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/imageUser/").path(uniqueFilename)
            .toUriString();

        user.setUrlProfile(url);
        user.setImageProfile(uniqueFilename);
      } else if (sampul != null && !sampul.isEmpty()) {
        if (sampulImage != null) {
          Path oldFile = roots.resolve(sampulImage);
          Files.deleteIfExists(oldFile);
        }
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
        String uniqueFilename = UUID.randomUUID().toString() + ext;
        Path filePath = this.roots.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), filePath);
        String urlSampul = ServletUriComponentsBuilder.fromCurrentContextPath().path("/imageSampul/").path(uniqueFilename)
            .toUriString();
        user.setSampulImage(uniqueFilename);
        user.setSampulUrl(urlSampul);
      }

      repository.save(user);
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
      return true;

    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
  }

  public void updateRole(Long roleId, Long userId) {
    String sql = "UPDATE public.user_roles SET role_id = ? WHERE user_id = ?";
    jdbcTemplate.update(sql, roleId, userId);
  }
  
}
