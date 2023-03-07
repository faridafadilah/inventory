package com.inventory.backend.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowRequest {
  private String nameRecipients;
  private String status;
  private int amount;
  private String date;
  private Long idList;
}
