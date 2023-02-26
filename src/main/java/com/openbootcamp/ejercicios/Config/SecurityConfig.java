package com.openbootcamp.ejercicios.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Security config es una clase que administra la gestion de autorizacion de endpoints
 * puedo crear usuarios para desarrollo, permiir roles, y acceso.
 * para autenticarse con usuarios predefinidos existen dos formas:
 *  1. Por medio de un navegador y un formulario habilitando el .formLogin()
 *  2. Por medio de una aplicacion como postman o rest client, en este caso es necesario
 *     agregar un mètodo que intercepte la peticiòn y autentique en el sistema.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApplicationConfiguration applicationConfiguration;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin();

        return http.build();
    }

    @Bean
    AuthenticationManager authManager(HttpSecurity http, PasswordEncoder passEncoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(applicationConfiguration.inMemoryUserDetailService(passwordEncoder()))
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
