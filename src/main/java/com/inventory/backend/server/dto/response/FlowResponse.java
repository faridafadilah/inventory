package com.inventory.backend.server.dto.response;

import lombok.Data;

@Data
public class FlowResponse {
  private String nameRecipients;
  private String status;
  private int amount;
  private String date;
}
