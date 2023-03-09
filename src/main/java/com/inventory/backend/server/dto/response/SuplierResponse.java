package com.inventory.backend.server.dto.response;

import com.inventory.backend.server.model.Suplier;

import lombok.Data;

@Data
public class SuplierResponse {
  private String name;
  private String phone;
  private String address;

  public static SuplierResponse getInstance(Suplier suplier) {
    SuplierResponse dto = new SuplierResponse();
    if(dto != null) {
      try {
        dto.setName(suplier.getName());
        dto.setPhone(suplier.getPhone());
        dto.setAddress(suplier.getAddress());
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
