package com.inventory.backend.server.services;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.ErrorCodeApi;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.FlowRequest;
import com.inventory.backend.server.dto.response.FlowResponse;
import com.inventory.backend.server.model.Flow;
import com.inventory.backend.server.model.ListBarang;
import com.inventory.backend.server.repository.BarangRepository;
import com.inventory.backend.server.repository.FlowRepository;

@Service
public class FlowService {
  @Autowired
  private FlowRepository repository;

  @Autowired
  private BarangRepository barangRepository;
  ModelMapper objectMapper = new ModelMapper();

  public boolean createFLow(FlowRequest req, ResponAPI<FlowResponse> responAPI) {
    Optional<ListBarang> barangOp = barangRepository.findById(req.getIdList());
    if (!barangOp.isPresent()) {
      responAPI.setErrorMessage("Barang not found!");
      return false;
    }
    try {
      ListBarang listBarang = barangOp.get();
      Flow flow = new Flow();
      flow.setNameRecipients(req.getNameRecipients());
      flow.setAmount(req.getAmount());
      flow.setDate(req.getDate());
      flow.setStatus(req.getStatus());
      flow.setListbarang(listBarang);

      if (listBarang.getQty() < 0) {
        responAPI.setErrorMessage("Data cannot be minus!");
        return false;
      }

      if (req.getStatus().equals("IN")) {
        listBarang.setQty(listBarang.getQty() + req.getAmount());
        responAPI.setErrorMessage("Success IN");
      } else if (req.getStatus().equals("OUT")) {
        listBarang.setQty(listBarang.getQty() - req.getAmount());
        responAPI.setErrorMessage("Success OUT");
      }
      barangRepository.save(listBarang);
      repository.save(flow);

      responAPI.setData(mapToFlowResponse(flow));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  private FlowResponse mapToFlowResponse(Flow flow) {
    return objectMapper.map(flow, FlowResponse.class);
  }

  public boolean updateFlow(FlowRequest req, ResponAPI<FlowResponse> responAPI, Long id) {
    Optional<Flow> flowOp = repository.findById(id);
    if (!flowOp.isPresent()) {
      responAPI.setErrorMessage("Transaction not found!");
      return false;
    }

    Optional<ListBarang> barangOp = barangRepository.findById(req.getIdList());
    if (!barangOp.isPresent()) {
      responAPI.setErrorMessage("Barang not found!");
      return false;
    }

    try {
      Flow flow = flowOp.get();
      ListBarang listBarang = barangOp.get();
      int currentAmount = flow.getAmount();
      int newAmount = req.getAmount();
      int amountDiff = newAmount - currentAmount;

      flow.setNameRecipients(req.getNameRecipients());
      flow.setAmount(req.getAmount());
      flow.setDate(req.getDate());
      flow.setStatus(req.getStatus());
      flow.setListbarang(listBarang);

      if (listBarang.getQty() < 0) {
        responAPI.setErrorMessage("Data cannot be minus!");
        return false;
      }

      if (listBarang.getQty() + amountDiff < 0) { // check if the new quantity is negative
        responAPI.setErrorMessage("Data cannot be reduced!");
        return false;
      }

      if (req.getStatus().equals("IN")) {
        listBarang.setQty(listBarang.getQty() + amountDiff);
        responAPI.setErrorMessage("Success IN");
      } else if (req.getStatus().equals("OUT")) {
        listBarang.setQty(listBarang.getQty() - amountDiff);
        responAPI.setErrorMessage("Success OUT");
      }
      barangRepository.save(listBarang);
      repository.save(flow);

      responAPI.setData(mapToFlowResponse(flow));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean deleteFlow(Long id, ResponAPI<FlowResponse> responAPI) {
    Optional<Flow> flowOp = repository.findById(id);
    if (!flowOp.isPresent()) {
      responAPI.setErrorMessage("Transaction not found!");
      return false;
    }

    try {
      Flow flow = flowOp.get();
      repository.delete(flow);
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage("Success Delete Transaction");
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

}
