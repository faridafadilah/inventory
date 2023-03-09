package com.inventory.backend.server.dto.response;
import com.inventory.backend.server.model.Category;
import com.inventory.backend.server.model.ListBarang;
import com.inventory.backend.server.model.Suplier;
import com.inventory.backend.server.model.User;

import lombok.Data;

@Data
public class ListBarangResponse {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
  private String image;
  private int qty;
  private CategoryResponse category;
  private SuplierResponse suplier;

  public static ListBarangResponse getInstance(ListBarang barang) {
    ListBarangResponse dto = new ListBarangResponse();
    if(dto != null) {
      try {
        dto.setId(barang.getId());
        dto.setName(barang.getName());
        dto.setDescription(barang.getDescription());
        dto.setImageUrl(barang.getUrlImage());
        dto.setImage(barang.getImage());
        dto.setQty(barang.getQty());

        CategoryResponse categorys = new CategoryResponse();
        categorys.setId(barang.getCategory().getId());
        categorys.setCategory(barang.getCategory().getCategory());
        dto.setCategory(categorys);

        SuplierResponse suplier = new SuplierResponse();
        suplier.setName(barang.getSuplier().getName());
        suplier.setPhone(barang.getSuplier().getPhone());
        suplier.setAddress(barang.getSuplier().getAddress());
        dto.setSuplier(suplier);
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
