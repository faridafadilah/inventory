package com.inventory.backend.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.FlowRequest;
import com.inventory.backend.server.dto.response.*;
import com.inventory.backend.server.services.FlowService;

@RestController
public class FLowController {

  @Autowired
  private FlowService service;

  @GetMapping("/getFlows")
  public ResponseEntity<ResponAPI<Page<FlowResponse>>> getAllFlows(
      @RequestParam(value = "search_query", required = false) String search,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "limit", required = false) Integer limit) {
    Page<FlowResponse> mainNotPending = service.getAllFlows(search, page, limit);
    ResponAPI<Page<FlowResponse>> responAPI = new ResponAPI<>();
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setData(mainNotPending);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @GetMapping("/getFlows/{id}")
  public ResponseEntity<ResponAPI<FlowResponse>> getFlowsById(@PathVariable("id") Long id) {
    ResponAPI<FlowResponse> responAPI = new ResponAPI<>();
    if (!service.getFlowById(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @GetMapping("/getFlow/{id}")
  public ResponseEntity<ResponAPI<Page<FlowResponse>>> getFlowsByIdBarang(
      @PathVariable("id") Long id,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "limit", required = false) Integer limit) {
    Page<FlowResponse> mainNotPending = service.getAllFlowsByIdBarang(page, limit, id);
    ResponAPI<Page<FlowResponse>> responAPI = new ResponAPI<>();
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setData(mainNotPending);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @PostMapping("/addFlow")
  public ResponseEntity<ResponAPI<FlowResponse>> createFlow(@RequestBody FlowRequest req) {
    ResponAPI<FlowResponse> responAPI = new ResponAPI();
    if (!service.createFLow(req, responAPI)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @PostMapping("/updateFlow/{id}")
  public ResponseEntity<ResponAPI<FlowResponse>> updateFlow(@RequestBody FlowRequest req, @PathVariable("id") Long id) {
    ResponAPI<FlowResponse> responAPI = new ResponAPI();
    if (!service.updateFlow(req, responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @DeleteMapping("/deleteFlow/{id}")
  public ResponseEntity<ResponAPI<FlowResponse>> deleteFlow(@PathVariable("id") Long id) {
    ResponAPI<FlowResponse> responAPI = new ResponAPI<>();
    if (!service.deleteFlow(id, responAPI)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }
}
