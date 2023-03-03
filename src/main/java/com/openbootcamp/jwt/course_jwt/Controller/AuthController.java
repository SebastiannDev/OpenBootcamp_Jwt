package com.openbootcamp.jwt.course_jwt.Controller;

import javax.management.relation.RoleNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openbootcamp.jwt.course_jwt.Services.Imp.UserService;
import com.openbootcamp.jwt.course_jwt.dto.LoginUser;
import com.openbootcamp.jwt.course_jwt.dto.NewUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/hello")
    String hello() {
        return "Hello";
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@Validated @RequestBody LoginUser loginUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
        }

        try {
            var serviceResponse = userService.login(loginUser);
            return new ResponseEntity<>(serviceResponse, HttpStatus.ACCEPTED);

        } catch (Exception e) {
            return new ResponseEntity<>("Error de autenticación verifique credenciales: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register")
    ResponseEntity<?> register(@Validated @RequestBody NewUser newUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Datos incompletos o mal formadas.", HttpStatus.BAD_REQUEST);
        }

        try {
            userService.register(newUser);
            return new ResponseEntity<>("Usuario registrado con exito", HttpStatus.CREATED);
        } catch (RoleNotFoundException e) {
            return new ResponseEntity<>("Rol incorrecto: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error al guardar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
