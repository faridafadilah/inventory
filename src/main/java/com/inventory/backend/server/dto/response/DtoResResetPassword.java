package com.inventory.backend.server.dto.response;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoResResetPassword {
  private String resetToken;
  private Date resetTokenDate;
}

