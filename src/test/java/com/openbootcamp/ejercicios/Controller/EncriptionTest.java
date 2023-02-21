package com.openbootcamp.ejercicios.Controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class EncriptionTest {
    
    @Test
    void bcryptTest() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var passwordEncoded = passwordEncoder.encode("admin");
        System.out.println(passwordEncoded);

        boolean match = passwordEncoder.matches("admin", passwordEncoded);
        System.out.println(match);
        
        assertTrue(match);
    }

    @Test
    void springPasswordEncoders(){
        Map<String, PasswordEncoder> encoder = new HashMap<>();
        encoder.put("bcrypt", new BCryptPasswordEncoder());
        encoder.put("pbkdf2", new Pbkdf2PasswordEncoder("s3cret", 2, 1, 2));

        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoder);
        String encodedPass = passwordEncoder.encode("test");
        System.out.println(encodedPass);
    }

}
