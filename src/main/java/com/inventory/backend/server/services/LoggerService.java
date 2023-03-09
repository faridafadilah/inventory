package com.inventory.backend.server.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.inventory.backend.server.dto.response.DtoResListLog;
import com.inventory.backend.server.repository.LoggRepository;
import com.inventory.backend.server.specification.LoggerSpecification;
import com.inventory.backend.server.model.*;
import com.inventory.backend.server.base.BasePageInterface;


@Service
public class LoggerService implements BasePageInterface<Logging, LoggerSpecification, DtoResListLog, Long> {
  @Autowired
  private LoggerSpecification specification;
  
  @Autowired
  private LoggRepository repository;

  public Page<DtoResListLog> getAllLog(String search, Integer page, Integer limit, List<String> sortBy, Boolean desc) {
    sortBy = (sortBy != null) ? sortBy : Arrays.asList("id");
    desc = (desc != null) ? desc : true;
    Pageable pageableRequest = this.defaultPage(search, page, limit, sortBy, desc);
    Page<Logging> settingPage = repository.findAll(this.defaultSpec(search, specification), pageableRequest);
    List<Logging> mains = settingPage.getContent();
    List<DtoResListLog> responseList = new ArrayList<>();
    mains.stream().forEach(a -> {
      responseList.add(DtoResListLog.getInstance(a));
    });
    Page<DtoResListLog> response = new PageImpl<>(responseList, pageableRequest, settingPage.getTotalElements());
    return response;
  }
  
}
