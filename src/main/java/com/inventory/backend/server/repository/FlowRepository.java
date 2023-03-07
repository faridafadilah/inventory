package com.inventory.backend.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.inventory.backend.server.model.Flow;

@Repository
public interface FlowRepository extends JpaRepository<Flow, Long>, JpaSpecificationExecutor<Flow> {
  
}
