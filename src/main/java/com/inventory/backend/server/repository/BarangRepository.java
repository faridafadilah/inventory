package com.inventory.backend.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.inventory.backend.server.model.ListBarang;

@Repository
public interface BarangRepository extends JpaRepository<ListBarang, Long>, JpaSpecificationExecutor<ListBarang> {
  Optional<ListBarang> findByName(String name);
}
