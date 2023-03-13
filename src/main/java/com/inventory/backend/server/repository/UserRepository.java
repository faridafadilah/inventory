package com.inventory.backend.server.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.inventory.backend.server.dto.response.*;

import com.inventory.backend.server.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
  @Query(value = "SELECT a.username as usernameUser, a.description as description, a.possition as possition, a.url_profile as urlImage, a.id as idUser, b.role_id as roleIdUser, c.name as roleUser FROM users a "+
    "INNER JOIN user_roles b ON a.id = b.user_id INNER JOIN roles c ON b.role_id = c.id ORDER BY a.id", nativeQuery = true) 
  List<DtoUserRole> getAllUserRole();

  Optional<User> findByUsername(String username);
  Boolean existsByUsername(String username); 
  Boolean existsByEmail(String email);

  @Query(value = "SELECT a.username as usernameUser, a.id as idUser, b.role_id as roleIdUser, c.name as roleUser "+
    "FROM users a " +
    "INNER JOIN user_roles b ON a.id = b.user_id " +
    "INNER JOIN roles c ON b.role_id = c.id " +
    "WHERE a.id = :userId "+
    "ORDER BY a.id", nativeQuery = true) 
  DtoUserRole getUserRole(Long userId);

  Optional<User> findByEmail(String email);
  Optional<User> findUsersByResetToken(String token);
}
