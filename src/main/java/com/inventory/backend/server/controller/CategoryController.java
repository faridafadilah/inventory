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
import com.inventory.backend.server.dto.request.CategoryRequest;
import com.inventory.backend.server.dto.response.*;
import com.inventory.backend.server.services.CategoryService;

@CrossOrigin("*")
@RestController
public class CategoryController {
  @Autowired
  private CategoryService service;

  @GetMapping("/getCategorys")
  public ResponseEntity<ResponAPI<Page<DtoResListCategory>>> getAllCategory(
      @RequestParam(value = "search_query", required = false) String search,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "limit", required = false) Integer limit) {
    Page<DtoResListCategory> mainNotPending = service.getAllCategory(search, page, limit);
    ResponAPI<Page<DtoResListCategory>> responAPI = new ResponAPI<>();
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setData(mainNotPending);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @GetMapping("/getCategory/{id}")
  public ResponseEntity<ResponAPI<CategoryResponse>> getDetailCategory(@PathVariable("id") Long id) {
    ResponAPI<CategoryResponse> responAPI = new ResponAPI<>();
    if (!service.getCategoryById(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    responAPI.setErrorCode(ErrorCode.SUCCESS);
    responAPI.setErrorMessage(MessageApi.SUCCESS);
    return ResponseEntity.status(HttpStatus.OK).body(responAPI);
  }

  @PostMapping("/addCategory")
  public ResponseEntity<ResponAPI<CategoryResponse>> createCategory(@RequestBody CategoryRequest category) {
    ResponAPI<CategoryResponse> responAPI = new ResponAPI();
    if (!service.createCategory(responAPI, category)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @PostMapping("/updateCategory/{id}")
  public ResponseEntity<ResponAPI<CategoryResponse>> updateCategory(@RequestBody String category,
      @PathVariable("id") Long id) {
    ResponAPI<CategoryResponse> responAPI = new ResponAPI();
    if (!service.updateCategory(responAPI, category, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @DeleteMapping("/deleteCategory/{id}")
  public ResponseEntity<ResponAPI<CategoryResponse>> deleteCategory(@PathVariable("id") Long id) {
    ResponAPI<CategoryResponse> responAPI = new ResponAPI();
    if (!service.deleteCategory(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

}
