package com.openbootcamp.ejercicios.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.openbootcamp.ejercicios.Model.Book;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Slf4j
public class BookControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void testCreate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        String json = """
                {
                    "title": "Libro test",
                    "author": "Test",
                    "pages": 650,
                    "price": 29.99,
                    "releaseDate": "2019-12-01",
                    "online": true
                }
                """;

        HttpEntity<String> request = new HttpEntity<>(json, headers);
        ResponseEntity<Book> response = testRestTemplate.exchange(
                "/api/books",
                HttpMethod.POST,
                request,
                Book.class);

        Book result = response.getBody();
        assertEquals(1L, result.getId());
        assertEquals("Libro test", result.getTitle());
    }

    @Test
    void testFindAll() {

        testRestTemplate = testRestTemplate.withBasicAuth("admin", "1234");

        ResponseEntity<Book[]> res = testRestTemplate.getForEntity("/api/books", Book[].class);
        assertEquals(HttpStatus.OK, res.getStatusCode());

        // verificando la lista devuelta en el body
        List<Book> books = Arrays.asList(res.getBody());
        log.info("Libros actuales en el test: " + books);
    }

    @Test
    void testFindOneById() {
        ResponseEntity<Book[]> res = testRestTemplate.getForEntity("/api/books/1", Book[].class);
        assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
    }
}
