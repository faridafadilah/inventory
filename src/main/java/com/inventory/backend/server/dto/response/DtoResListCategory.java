package com.inventory.backend.server.dto.response;

import java.util.*;

import com.inventory.backend.server.model.Category;
import com.inventory.backend.server.model.ListBarang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoResListCategory {
  private Long id;
  private String category;
  private List<ListBarangResponse> barangs;

  public static DtoResListCategory getInstance(Category category) {
    DtoResListCategory dto = new DtoResListCategory();
    if(category != null) {
      try {
       dto.setId(category.getId());
       dto.setCategory(category.getCategory());
        
        List<ListBarangResponse> barangs = new ArrayList<>();
        if(category.getListbarangList() != null) {
          if(!category.getListbarangList().isEmpty()) {
            for(ListBarang barang : category.getListbarangList()) {
              ListBarangResponse barangResponse = new ListBarangResponse();
              barangResponse.setId(barang.getId());
              barangResponse.setName(barang.getName());
              barangResponse.setDescription(barang.getDescription());
              barangResponse.setImageUrl(barang.getUrlImage());
              barangResponse.setImage(barang.getImage());
              barangResponse.setQty(barang.getQty());

              CategoryResponse categorys = new CategoryResponse();
              categorys.setId(barang.getCategory().getId());
              categorys.setCategory(barang.getCategory().getCategory());
              barangResponse.setCategory(categorys);

              SuplierResponse supliers = new SuplierResponse();
              supliers.setName(barang.getSuplier().getName());
              supliers.setPhone(barang.getSuplier().getPhone());
              supliers.setAddress(barang.getSuplier().getAddress());
              barangResponse.setSuplier(supliers);
              barangs.add(barangResponse);
            }
          }
        }
        dto.setBarangs(barangs);
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
