package com.openbootcamp.jwt.course_jwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openbootcamp.jwt.course_jwt.Model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
  
    Optional<Role> findOneByRole(String roleName);
}
