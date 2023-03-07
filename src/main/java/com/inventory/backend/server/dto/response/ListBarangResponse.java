package com.inventory.backend.server.dto.response;
import com.inventory.backend.server.model.Category;
import com.inventory.backend.server.model.Suplier;
import com.inventory.backend.server.model.User;

import lombok.Data;

@Data
public class ListBarangResponse {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
  private int qty;
  private CategoryResponse category;
  private SuplierResponse suplier;
}
