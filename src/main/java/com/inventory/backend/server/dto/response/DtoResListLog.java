package com.inventory.backend.server.dto.response;

import com.inventory.backend.server.model.Logging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoResListLog {
  private int statusCode;
  private String error;
  private String method;
  private String url;
  private String date;
  private String username;

  public static DtoResListLog getInstance(Logging log) {
    DtoResListLog dto = new DtoResListLog();
    if(dto != null) {
      try {
        dto.setStatusCode(log.getStatusCode());
        dto.setError(log.getError());
        dto.setMethod(log.getMethod());
        dto.setUrl(log.getUrl());
        dto.setDate(log.getDate());
        dto.setUsername(log.getUsername());
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
