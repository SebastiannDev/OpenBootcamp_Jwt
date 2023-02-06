package com.openbootcamp.ejercicios.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openbootcamp.ejercicios.Model.Entities.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
