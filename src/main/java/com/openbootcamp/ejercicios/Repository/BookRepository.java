package com.openbootcamp.ejercicios.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openbootcamp.ejercicios.Model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
