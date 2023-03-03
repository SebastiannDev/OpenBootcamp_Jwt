package com.openbootcamp.jwt.course_jwt.Controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

    private static final String message = "Hello World in spring security";
    @GetMapping("/hello")
    ResponseEntity<?> greetings(){
        return new ResponseEntity<String>(message, HttpStatusCode.valueOf(200));
    }
}
