package com.openbootcamp.ejercicios.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openbootcamp.ejercicios.Model.Entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
