package com.openbootcamp.ejercicios.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openbootcamp.ejercicios.Model.Entities.User;
import com.openbootcamp.ejercicios.Model.Repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {

    private UserRepository userRepository;
    
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    ResponseEntity<User> createUser(@RequestBody User User){
        return ResponseEntity.ok(userRepository.save(User));
    }

    @GetMapping("/user")
    ResponseEntity<List<User>> findAllUsers(){
        var user = userRepository.findAll();
        return ResponseEntity.ok().body(user);
    }
}
