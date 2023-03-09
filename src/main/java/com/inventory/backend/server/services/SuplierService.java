package com.inventory.backend.server.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventory.backend.server.base.BasePageInterface;
import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.ErrorCodeApi;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.SuplierRequest;
import com.inventory.backend.server.dto.response.DtoResListSuplier;
import com.inventory.backend.server.dto.response.SuplierResponse;
import com.inventory.backend.server.model.Suplier;
import com.inventory.backend.server.repository.SuplierRepository;
import com.inventory.backend.server.specification.SuplierSpecification;

@Service
public class SuplierService implements BasePageInterface<Suplier, SuplierSpecification, SuplierResponse, Long> {
  @Autowired
  private SuplierRepository repository;
  ModelMapper objectMapper = new ModelMapper();

  @Autowired
  private SuplierSpecification specification;

  public boolean createSuplier(ResponAPI<SuplierResponse> responAPI, SuplierRequest req) {
    try {
      Suplier suplier = new Suplier();
      suplier.setName(req.getName());
      suplier.setPhone(req.getPhone());
      suplier.setAddress(req.getAddress());
      repository.save(suplier);
      
      responAPI.setData(mapToSuplierResponse(suplier));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  private SuplierResponse mapToSuplierResponse(Suplier categori) {
    return objectMapper.map(categori, SuplierResponse.class);
  }

  public boolean updateSuplier(ResponAPI<SuplierResponse> responAPI, SuplierRequest req, Long id) {
    Optional<Suplier> supOptional = repository.findById(id);
    if (!supOptional.isPresent()) {
      responAPI.setErrorMessage("Suplier Not Found!");
      return false;
    }
    try {
      Suplier suplier = supOptional.get();
      suplier.setName(req.getName());
      suplier.setPhone(req.getPhone());
      suplier.setAddress(req.getAddress());
      repository.save(suplier);

      responAPI.setData(mapToSuplierResponse(suplier));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean deleteSuplier(ResponAPI<SuplierResponse> responAPI, Long id) {
    Optional<Suplier> supOptional = repository.findById(id);
    if (!supOptional.isPresent()) {
      responAPI.setErrorMessage("Suplier Not Found!");
      return false;
    }
    try {
      Suplier suplier = supOptional.get();
      repository.delete(suplier);
      
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  public Page<DtoResListSuplier> getAllSuplier(String search, Integer page, Integer limit) {
    List<String> sortBy = Arrays.asList("id");
    boolean desc = true;
    Pageable pageableRequest = this.defaultPage(search, page, limit, sortBy, desc);
    Page<Suplier> settingPage = repository.findAll(this.defaultSpec(search, specification), pageableRequest);
    List<Suplier> mains = settingPage.getContent();
    List<DtoResListSuplier> responseList = new ArrayList<>();
    mains.stream().forEach(a -> {
      responseList.add(DtoResListSuplier.getInstance(a));
    });
    Page<DtoResListSuplier> response = new PageImpl<>(responseList, pageableRequest, settingPage.getTotalElements());
    return response;
  }

  public boolean getSuplier(ResponAPI<SuplierResponse> responAPI, Long id) {
    Optional<Suplier> supOptional = repository.findById(id);
    if (!supOptional.isPresent()) {
      responAPI.setErrorMessage("Suplier Not Found!");
      return false;
    }
    try {
      SuplierResponse response = SuplierResponse.getInstance(supOptional.get());
      responAPI.setData(response);
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }
  
}
