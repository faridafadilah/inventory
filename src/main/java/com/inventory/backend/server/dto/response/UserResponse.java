package com.inventory.backend.server.dto.response;

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
}
