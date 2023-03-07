package com.inventory.backend.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.inventory.backend.server.base.ResponAPI;
import com.inventory.backend.server.dto.request.BarangRequest;
import com.inventory.backend.server.dto.response.ListBarangResponse;
import com.inventory.backend.server.services.ListBarangService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// router.get("/getList", auth, morganMiddleware, getLists);
// router.get("/getLength", auth, morganMiddleware, getLength);
// router.get("/getAlldata", auth, morganMiddleware, Alldata);
// router.get("/getList/:id", auth, morganMiddleware, getlistsOne);
// router.get("/getLists/:name", auth, morganMiddleware, getlistsOnes);

@RestController
public class BarangController {
//   {
//     "name": "botol",
//     "description": "botol bekas",
//     "idCategory": 1,
//     "idSuplier": 1,
//     "qty": 20,
//     "idUser": 1
// }
  @Autowired
  private ListBarangService service;

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
