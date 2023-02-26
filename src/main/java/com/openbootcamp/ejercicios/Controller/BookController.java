package com.openbootcamp.ejercicios.Controller;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openbootcamp.ejercicios.Model.Book;
import com.openbootcamp.ejercicios.Repository.BookRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
public class BookController {

    @Value("${app.message}")
    private final String message = null;

    private final BookRepository bookRepository;
    private final Logger log = LoggerFactory.getLogger(BookController.class);

    // Constructor
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Hello
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello from spring boot!");
    }

    // Read All
    @Operation(summary = "Get a list of books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books founded"),
            @ApiResponse(responseCode = "404", description = "Books not founded")
    })
    @GetMapping("/books")
    public List<Book> findAll() {
        System.out.println(message);
        return bookRepository.findAll();
    }

    // Read One
    @Operation(summary = "Get one book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book founded", content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "Book not founded")
    })
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> findOneById(
            @Parameter(description = "id of book to be searched") @PathVariable Long id) {

        var response = bookRepository.findById(id);

        if (response.isPresent()) {
            log.info("Reading one book...");
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create One
    @PostMapping("/books")
    public ResponseEntity<Book> create(@RequestBody @NotNull Book book) {

        if (book.getId() == null) {
            log.warn("Trying create new book...");
            return ResponseEntity.ok(bookRepository.save(book));
        }

        return ResponseEntity.badRequest().build();
    }

    // Update One
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> update(@RequestBody Book book, @PathVariable Long id) {

        if (id == null) {
            log.warn("Trying update one book not existent");
            return ResponseEntity.badRequest().build();
        }

        if (bookRepository.existsById(id)) {
            return ResponseEntity.ok(bookRepository.save(book));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {

        if (!bookRepository.existsById(id)) {
            log.warn("Intentando borrar un libro inexistente");
            return ResponseEntity.notFound().build();
        }

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/books")
    public ResponseEntity<Book> deleteAll() {

        log.info("REST peticion para borrar todos los libros");
        bookRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
