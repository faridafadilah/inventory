package com.inventory.backend.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.SuplierRequest;
import com.inventory.backend.server.dto.response.DtoResListSuplier;
import com.inventory.backend.server.dto.response.SuplierResponse;
import com.inventory.backend.server.services.SuplierService;

@CrossOrigin("*")
@RestController
public class SuplierController {
  @Autowired
  private SuplierService service;
  
  @GetMapping("/getSupliers")
  public ResponseEntity<ResponAPI<Page<DtoResListSuplier>>> getAllSuplier(
      @RequestParam(value = "search_query", required = false) String search,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "limit", required = false) Integer limit) {
    Page<DtoResListSuplier> mainNotPending = service.getAllSuplier(search, page, limit);
    ResponAPI<Page<DtoResListSuplier>> responAPI = new ResponAPI<>();
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setData(mainNotPending);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @GetMapping("/getSuplier/{id}")
  public ResponseEntity<ResponAPI<SuplierResponse>> getSuplier(@PathVariable("id") Long id) {
    ResponAPI<SuplierResponse> responAPI = new ResponAPI<>();
    if (!service.getSuplier(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @PostMapping("/addSuplier")
  public ResponseEntity<ResponAPI<SuplierResponse>> createSuplier(@RequestBody SuplierRequest req) {
    ResponAPI<SuplierResponse> responAPI = new ResponAPI();
    if(!service.createSuplier(responAPI, req)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @PostMapping("/updateSuplier/{id}")
  public ResponseEntity<ResponAPI<SuplierResponse>> updateSuplier(@RequestBody SuplierRequest req, @PathVariable("id") Long id) {
    ResponAPI<SuplierResponse> responAPI = new ResponAPI<>();
    if(!service.updateSuplier(responAPI, req, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @DeleteMapping("/deleteSuplier/{id}")
  public ResponseEntity<ResponAPI<SuplierResponse>> deleteSuplier( @PathVariable("id") Long id) {
    ResponAPI<SuplierResponse> responAPI = new ResponAPI<>();
    if(!service.deleteSuplier(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }
}
