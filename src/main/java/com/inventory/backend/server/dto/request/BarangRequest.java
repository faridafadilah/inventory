package com.inventory.backend.server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BarangRequest {
  private String name;
  private String description;
  private Long idCategory;
  private Long idSuplier;
  private int qty;
  private Long idUser;
}
