package com.inventory.backend.server.dto.response;

import com.inventory.backend.server.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
  private Long id;
  private String username;
  private String email;
  private String name;
  private String phone;
  private String gender;
  private String address;
  private String description;
  private String possition;
  private String roles;
  private String imageUser;
  private String urlUser;
  private String sampulUser;
  private String urlSampul;

  public static UserResponse getInstance(User user) {
    UserResponse dto = new UserResponse();
    if(dto != null) {
      try {
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAddress(user.getAddress());
        dto.setDescription(user.getDescription());
        dto.setEmail(user.getEmail());
        dto.setGender(user.getGender());
        dto.setPhone(user.getPhone());
        dto.setPossition(user.getPossition());
        dto.setRoles(user.getRoles().toString());
        dto.setUsername(user.getUsername());
        dto.setImageUser(user.getImageProfile());
        dto.setUrlUser(user.getUrlProfile());
        dto.setSampulUser(user.getSampulImage());
        dto.setUrlSampul(user.getSampulUrl());
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
