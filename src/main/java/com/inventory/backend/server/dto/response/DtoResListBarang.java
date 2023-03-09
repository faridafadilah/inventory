package com.inventory.backend.server.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.inventory.backend.server.model.Flow;
import com.inventory.backend.server.model.ListBarang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoResListBarang {
  private Long id;
  private String name;
  private String description;
  private String imageUrl;
  private String image;
  private int qty;
  private CategoryResponse category;
  private SuplierResponse suplier;
  private UserResponse users;
  private List<FlowResponse> flows;

  public static DtoResListBarang getInstance(ListBarang barang) {
    DtoResListBarang dto = new DtoResListBarang();
    if(barang != null) {
      try {
        dto.setId(barang.getId());
        dto.setName(barang.getName());
        dto.setDescription(barang.getDescription());
        dto.setImageUrl(barang.getUrlImage());
        dto.setImage(barang.getImage());
        dto.setQty(barang.getQty());

        UserResponse user = new UserResponse();
        user.setId(barang.getUsers().getId());
        user.setName(barang.getUsers().getName());
        user.setAddress(barang.getUsers().getAddress());
        user.setDescription(barang.getUsers().getDescription());
        user.setEmail(barang.getUsers().getEmail());
        user.setGender(barang.getUsers().getGender());
        user.setPhone(barang.getUsers().getPhone());
        user.setUsername(barang.getUsers().getUsername());
        user.setPossition(barang.getUsers().getPossition());
        user.setRoles(barang.getUsers().getRoles().toString());

        CategoryResponse categorys = new CategoryResponse();
        categorys.setId(barang.getCategory().getId());
        categorys.setCategory(barang.getCategory().getCategory());
        dto.setCategory(categorys);

        SuplierResponse suplier = new SuplierResponse();
        suplier.setName(barang.getSuplier().getName());
        suplier.setPhone(barang.getSuplier().getPhone());
        suplier.setAddress(barang.getSuplier().getAddress());
        dto.setSuplier(suplier);

        List<FlowResponse> flows = new ArrayList<>();
        if(barang.getFlowList() != null) {
          if(!barang.getFlowList().isEmpty()) {
            for(Flow flow : barang.getFlowList()) {
              FlowResponse flowResponse = new FlowResponse();
              flowResponse.setNameRecipients(flow.getNameRecipients());
              flowResponse.setStatus(flow.getStatus());
              flowResponse.setAmount(flow.getAmount());
              flowResponse.setDate(flow.getDate());
              flows.add(flowResponse);
            }
          }
        }
        dto.setFlows(flows);
        return dto;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
