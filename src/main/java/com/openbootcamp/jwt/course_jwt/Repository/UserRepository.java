package com.openbootcamp.jwt.course_jwt.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openbootcamp.jwt.course_jwt.Model.User;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findOneByEmail(String email);
}
