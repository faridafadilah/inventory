package com.inventory.backend.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.dto.request.SuplierRequest;
import com.inventory.backend.server.dto.response.SuplierResponse;
import com.inventory.backend.server.services.SuplierService;

@RestController
public class SuplierController {
  @Autowired
  private SuplierService service;

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

// router.get("/getSupliers", auth, morganMiddleware, getSupliers);
// router.get("/getSuplier/:id", auth, morganMiddleware, getSuplier);
}
