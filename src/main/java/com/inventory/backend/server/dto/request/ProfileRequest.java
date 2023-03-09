package com.inventory.backend.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
  private String email;
  private String name;
  private String phone;
  private String gender;
  private String address;
  private String description;
  private String possition;
}
