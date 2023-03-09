package com.inventory.backend.server.dto.response;

import com.inventory.backend.server.model.Flow;

import lombok.Data;

@Data
public class FlowResponse {
  private String nameRecipients;
  private String status;
  private int amount;
  private String date;

  public static FlowResponse getInstance(Flow flow) {
    FlowResponse dto = new FlowResponse();
    if(dto != null) {
      try {
        dto.setNameRecipients(flow.getNameRecipients());
        dto.setStatus(flow.getStatus());
        dto.setAmount(flow.getAmount());
        dto.setDate(flow.getDate());
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
