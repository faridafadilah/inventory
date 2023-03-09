package com.inventory.backend.server.dto.response;

import com.inventory.backend.server.model.Category;

import lombok.Data;

@Data
public class CategoryResponse {
  private Long id;
  private String category;

  public static CategoryResponse getInstance(Category category) {
    CategoryResponse dto = new CategoryResponse();
    if(dto != null) {
      try {
        dto.setId(category.getId());
        dto.setCategory(category.getCategory());
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
