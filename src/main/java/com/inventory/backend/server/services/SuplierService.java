package com.inventory.backend.server.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.ErrorCodeApi;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.SuplierRequest;
import com.inventory.backend.server.dto.response.SuplierResponse;
import com.inventory.backend.server.model.Suplier;
import com.inventory.backend.server.repository.SuplierRepository;

@Service
public class SuplierService {
  @Autowired
  private SuplierRepository repository;
  ModelMapper objectMapper = new ModelMapper();

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
  
}
