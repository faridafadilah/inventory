package com.inventory.backend.server.services;

import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.backend.server.base.BasePageInterface;
import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.constant.ErrorCode;
import com.inventory.backend.server.constant.ErrorCodeApi;
import com.inventory.backend.server.constant.MessageApi;
import com.inventory.backend.server.dto.request.BarangRequest;
import com.inventory.backend.server.dto.response.*;
import com.inventory.backend.server.model.*;
import com.inventory.backend.server.repository.*;
import com.inventory.backend.server.specification.BarangSpecification;

@Service
public class ListBarangService implements BasePageInterface<ListBarang, BarangSpecification, ListBarangResponse, Long> {

  @Autowired
  private BarangRepository repository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private SuplierRepository suplierRepository;

  @Autowired
  private UserRepository userRepository;
  private ModelMapper objectMapper = new ModelMapper();

  @Autowired
  private BarangSpecification specification;

  public boolean createListBarang(BarangRequest req, ResponAPI<ListBarangResponse> responAPI, MultipartFile file) {
    Optional<ListBarang> barangOptional = repository.findByName(req.getName());
    if (barangOptional.isPresent()) {
      responAPI.setErrorMessage("Barang sudah tersedia!");
      return false;
    }

    Optional<Category> categoryOptional = categoryRepository.findById(req.getIdCategory());
    if (!categoryOptional.isPresent()) {
      responAPI.setErrorMessage("Category not found!");
      return false;
    }

    Optional<Suplier> suplierOptional = suplierRepository.findById(req.getIdSuplier());
    if (!suplierOptional.isPresent()) {
      responAPI.setErrorMessage("Suplier not found!");
      return false;
    }

    Optional<User> userOptional = userRepository.findById(req.getIdUser());
    if (!userOptional.isPresent()) {
      responAPI.setErrorMessage("Suplier not found!");
      return false;
    }

    try {
      String fileName = file.getOriginalFilename();
      String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
      String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
      Path path = Paths.get("uploads/" + newFileName);
      Files.copy(file.getInputStream(), path);

      ListBarang barang = new ListBarang();
      barang.setName(req.getName());
      barang.setDescription(req.getDescription());
      barang.setCategory(categoryOptional.get());
      barang.setSuplier(suplierOptional.get());
      barang.setUsers(userOptional.get());
      barang.setImage(newFileName);
      barang.setQty(req.getQty());
      barang.setUrlImage("http://localhost:8080/uploads/" + newFileName);
      repository.save(barang);

      responAPI.setData(mapToListBarangResponse(barang));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  private ListBarangResponse mapToListBarangResponse(ListBarang listBarang) {
    ModelMapper modelMapper = new ModelMapper();
    ListBarangResponse response = modelMapper.map(listBarang, ListBarangResponse.class);
    response.setImageUrl("http://localhost:8080/uploads/" + listBarang.getImage());
    response.setCategory(mapToCategoryResponse(listBarang.getCategory()));
    response.setSuplier(mapToSuplierResponse(listBarang.getSuplier()));
    return response;
  }
  
  private CategoryResponse mapToCategoryResponse(Category category) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(category, CategoryResponse.class);
  }
  
  private SuplierResponse mapToSuplierResponse(Suplier suplier) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(suplier, SuplierResponse.class);
  }

  public boolean updateListBarang(BarangRequest req, ResponAPI<ListBarangResponse> responAPI, MultipartFile file, Long id) {
    if(req.getQty() < 1 || req.getQty() == '-'){
      responAPI.setErrorMessage("Quantity < 0 atau kosong");
      return false;
    }

    Optional<ListBarang> barangOptional = repository.findById(id);
    if (!barangOptional.isPresent()) {
      responAPI.setErrorMessage("Barang not found!");
      return false;
    }

    Optional<ListBarang> barangOp = repository.findById(id);
    if (!barangOp.isPresent()) {
      responAPI.setErrorMessage("Barang with id not found!");
      return false;
    }

    Optional<Category> categoryOptional = categoryRepository.findById(req.getIdCategory());
    if (!categoryOptional.isPresent()) {
      responAPI.setErrorMessage("Category not found!");
      return false;
    }

    Optional<Suplier> suplierOptional = suplierRepository.findById(req.getIdSuplier());
    if (!suplierOptional.isPresent()) {
      responAPI.setErrorMessage("Suplier not found!");
      return false;
    }

    Optional<User> userOptional = userRepository.findById(req.getIdUser());
    if (!userOptional.isPresent()) {
      responAPI.setErrorMessage("Suplier not found!");
      return false;
    }

    try {
      String fileName = file.getOriginalFilename();
     
      ListBarang barang = barangOp.get();
      String barangImage = barang.getImage();
      if(barangImage != fileName){
        // menghapus file lama
        File fileToDelete = new File("uploads/" + barangImage);
        fileToDelete.delete();

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + fileExtension;
        Path path = Paths.get("uploads/" + newFileName);
        Files.copy(file.getInputStream(), path);
        barang.setImage(newFileName);
        barang.setUrlImage("http://localhost:8080/uploads/" + newFileName);
      }
      barang.setName(req.getName());
      barang.setDescription(req.getDescription());
      barang.setCategory(categoryOptional.get());
      barang.setSuplier(suplierOptional.get());
      barang.setUsers(userOptional.get());
      barang.setQty(req.getQty());
      repository.save(barang);

      responAPI.setData(mapToListBarangResponse(barang));
      responAPI.setErrorCode(ErrorCode.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean deleteBarangById(ResponAPI<String> responAPI, Long id) {
    Optional<ListBarang> barangOp = repository.findById(id);
    if (!barangOp.isPresent()) {
      responAPI.setErrorMessage("Barang with id not found!");
      return false;
    }
    try {
      ListBarang barang = barangOp.get();
      String barangImage = barang.getImage();
      File fileToDelete = new File("uploads/" + barangImage);
      fileToDelete.delete();
      repository.delete(barang);
      
      responAPI.setErrorCode(ErrorCode.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  public Page<DtoResListBarang> getAllBarang(String search, Integer page, Integer limit) {
    List<String> sortBy = Arrays.asList("id");
    boolean desc = true;
    Pageable pageableRequest = this.defaultPage(search, page, limit, sortBy, desc);
    Page<ListBarang> settingPage = repository.findAll(this.defaultSpec(search, specification), pageableRequest);
    List<ListBarang> mains = settingPage.getContent();
    List<DtoResListBarang> responseList = new ArrayList<>();
    mains.stream().forEach(a -> {
      responseList.add(DtoResListBarang.getInstance(a));
    });
    Page<DtoResListBarang> response = new PageImpl<>(responseList, pageableRequest, settingPage.getTotalElements());
    return response;
  }

  public boolean getBarangDetail(ResponAPI<ListBarangResponse> responAPI, Long id) {
    Optional<ListBarang> barangOp = repository.findById(id);
    if (!barangOp.isPresent()) {
      responAPI.setErrorMessage("Barang with id not found!");
      return false;
    }
    try {
      ListBarangResponse response = ListBarangResponse.getInstance(barangOp.get());
      responAPI.setData(response);
      responAPI.setErrorCode(ErrorCode.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

  public boolean getAllLength(ResponAPI<SizeAllResponse> responAPI) {
    try {
      List<ListBarang> listBarangs = repository.findAll();
      List<Suplier> supliers = suplierRepository.findAll();
      List<Category> categories = categoryRepository.findAll();
      List<User> users = userRepository.findAll();

      SizeAllResponse response = new SizeAllResponse();
      response.setProductDataBarang(listBarangs.size());
      response.setProductCategory(categories.size());
      response.setProductSuplier(supliers.size());
      response.setProductUser(users.size());

      responAPI.setData(response);
      responAPI.setErrorCode(ErrorCodeApi.SUCCESS);
      responAPI.setErrorMessage(MessageApi.SUCCESS);
    } catch (Exception e) {
      responAPI.setErrorCode(ErrorCodeApi.FAILED);
      responAPI.setErrorMessage(e.getMessage());
      return false;
    }
    return true;
  }

}
