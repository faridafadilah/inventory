package com.inventory.backend.server.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.ErrorCodeApi;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.CategoryRequest;
import com.inventory.backend.server.dto.response.CategoryResponse;
import com.inventory.backend.server.repository.*;
import com.inventory.backend.server.model.*;

import java.util.*;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository repository;
  ModelMapper objectMapper = new ModelMapper();

  public boolean createCategory(ResponAPI<CategoryResponse> responAPI, CategoryRequest category) {
    Optional<Category> catOptional = repository.findByCategory(category.getCategory());
    if (catOptional.isPresent()) {
      responAPI.setErrorMessage("Category Sudah tersedia!");
      return false;
    }
    try {
      Category categori = new Category();
      categori.setCategory(category.getCategory());
      repository.save(categori);

      responAPI.setData(mapToCategoryResponse(categori));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  private CategoryResponse mapToCategoryResponse(Category cat) {
    return objectMapper.map(cat, CategoryResponse.class);
  }

  public boolean updateCategory(ResponAPI<CategoryResponse> responAPI, String category, Long id) {
    Optional<Category> catOptional = repository.findById(id);
    if (!catOptional.isPresent()) {
      responAPI.setErrorMessage("Category Not Found!");
      return false;
    }
    try {
      Category categoris = catOptional.get();
      categoris.setCategory(category);
      repository.save(categoris);

      responAPI.setData(mapToCategoryResponse(categoris));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean deleteCategory(ResponAPI<CategoryResponse> responAPI, Long id) {
    Optional<Category> catOptional = repository.findById(id);
    if (!catOptional.isPresent()) {
      responAPI.setErrorMessage("Category Not Found!");
      return false;
    }
    try {
      Category categoris = catOptional.get();
      repository.delete(categoris);
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
