package com.inventory.backend.server.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.inventory.backend.server.model.ListBarang;
import com.inventory.backend.server.model.Suplier;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoResListSuplier {
  private String name;
  private String phone;
  private String address;
  private List<ListBarangResponse> listbarangList;

  public static DtoResListSuplier getInstance(Suplier suplier) {
    DtoResListSuplier dto = new DtoResListSuplier();
    if(suplier != null) {
      try {
       dto.setName(suplier.getName());
       dto.setAddress(suplier.getAddress());
       dto.setPhone(suplier.getPhone());
        
        List<ListBarangResponse> barangs = new ArrayList<>();
        if(suplier.getListbarangList() != null) {
          if(!suplier.getListbarangList().isEmpty()) {
            for(ListBarang barang : suplier.getListbarangList()) {
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
        dto.setListbarangList(barangs);
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
