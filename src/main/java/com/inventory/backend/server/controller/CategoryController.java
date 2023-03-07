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
import com.inventory.backend.server.dto.request.CategoryRequest;
import com.inventory.backend.server.dto.response.CategoryResponse;
import com.inventory.backend.server.services.CategoryService;

@RestController
public class CategoryController {
  @Autowired
  private CategoryService service;

  @PostMapping("/addCategory")
  public ResponseEntity<ResponAPI<CategoryResponse>> createCategory(@RequestBody CategoryRequest category) {
    ResponAPI<CategoryResponse> responAPI = new ResponAPI();
    if(!service.createCategory(responAPI, category)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @PostMapping("/updateCategory/{id}")
  public ResponseEntity<ResponAPI<CategoryResponse>> updateCategory(@RequestBody String category, @PathVariable("id") Long id) {
    ResponAPI<CategoryResponse> responAPI = new ResponAPI();
    if(!service.updateCategory(responAPI, category, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  @DeleteMapping("/deleteCategory/{id}")
  public ResponseEntity<ResponAPI<CategoryResponse>> deleteCategory(@PathVariable("id") Long id) {
    ResponAPI<CategoryResponse> responAPI = new ResponAPI();
    if(!service.deleteCategory(responAPI, id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responAPI);
    }
    return ResponseEntity.ok(responAPI);
  }

  
// router.get("/getCategorys", auth, morganMiddleware, getCategorys);
// router.get("/getCategory/:id", auth, morganMiddleware, getCategory);
}
