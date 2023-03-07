package com.inventory.backend.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuplierRequest {
  private String name;
  private String phone;
  private String address;
}
