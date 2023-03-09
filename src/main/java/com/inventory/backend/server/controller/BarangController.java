package com.inventory.backend.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.BarangRequest;
import com.inventory.backend.server.dto.response.*;
import com.inventory.backend.server.services.ListBarangService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class BarangController {
  @Autowired
  private ListBarangService service;

  @GetMapping("/getList")
  public ResponseEntity<ResponAPI<Page<DtoResListBarang>>> getAllBarang(
      @RequestParam(value = "search_query", required = false) String search,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "limit", required = false) Integer limit) {
    Page<DtoResListBarang> mainNotPending = service.getAllBarang(search, page, limit);
    ResponAPI<Page<DtoResListBarang>> responAPI = new ResponAPI<>();
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setData(mainNotPending);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @GetMapping("/getLength")
  public ResponseEntity<ResponAPI<SizeAllResponse>> getLengthAll() {
    ResponAPI<SizeAllResponse> responAPI = new ResponAPI<>();
    if(!service.getAllLength(responAPI)){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @GetMapping("/getList/{id}")
  public ResponseEntity<ResponAPI<ListBarangResponse>> getBarangDetail(@PathVariable("id") Long id) {
    ResponAPI<ListBarangResponse> responAPI = new ResponAPI<>();
    if (!service.getBarangDetail(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @PostMapping(value="/addList")
  public ResponseEntity<ResponAPI<ListBarangResponse>> createListBarang(@ModelAttribute BarangRequest req, @RequestParam("file") MultipartFile file) {
    ResponAPI<ListBarangResponse> responAPI = new ResponAPI();
    if(!service.createListBarang(req, responAPI, file)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @PostMapping("/updateList/{id}")
  public ResponseEntity<ResponAPI<ListBarangResponse>> updateListBarang(@ModelAttribute BarangRequest req, @RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {
    ResponAPI<ListBarangResponse> responAPI = new ResponAPI();
    if(!service.updateListBarang(req, responAPI, file, id)){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @DeleteMapping("/deleteList/{id}")
  public ResponseEntity<ResponAPI<String>> deleteBarangById(@PathVariable("id") Long id) {
    ResponAPI<String> responAPI = new ResponAPI<>();
    if(!service.deleteBarangById(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }
  
}
