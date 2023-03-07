package com.inventory.backend.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.dto.request.FlowRequest;
import com.inventory.backend.server.dto.response.*;
import com.inventory.backend.server.services.FlowService;

@RestController
public class FLowController {
  //router CRUD Flow start
// router.get("/getFlows", auth, morganMiddleware, getFlows);
// router.get("/getFlow/:id", auth, morganMiddleware, getFlow);
// router.get("/getFlows/:id", auth, morganMiddleware, getFlowsOne);

  @Autowired
  private FlowService service;

  @PostMapping("/addFlow")
  public ResponseEntity<ResponAPI<FlowResponse>> createFlow(@RequestBody FlowRequest req) {
    ResponAPI<FlowResponse> responAPI = new ResponAPI();
    if(!service.createFLow(req, responAPI)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @PostMapping("/updateFlow/{id}")
  public ResponseEntity<ResponAPI<FlowResponse>> updateFlow(@RequestBody FlowRequest req, @PathVariable("id") Long id) {
    ResponAPI<FlowResponse> responAPI = new ResponAPI();
    if(!service.updateFlow(req, responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @DeleteMapping("/deleteFlow/{id}")
  public ResponseEntity<ResponAPI<FlowResponse>> deleteFlow(@PathVariable("id") Long id) {
    ResponAPI<FlowResponse> responAPI = new ResponAPI<>();
    if(!service.deleteFlow(id, responAPI)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }
}
