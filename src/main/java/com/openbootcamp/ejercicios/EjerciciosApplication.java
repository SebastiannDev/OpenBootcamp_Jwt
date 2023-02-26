package com.openbootcamp.ejercicios;

import com.openbootcamp.ejercicios.Model.Role;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openbootcamp.ejercicios.Model.User;
import com.openbootcamp.ejercicios.Repository.UserRepository;

@SpringBootApplication
public class EjerciciosApplication {


	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(EjerciciosApplication.class, args);

		UserRepository userRepo = app.getBean(UserRepository.class);
		PasswordEncoder encoder = app.getBean(PasswordEncoder.class);

		userRepo.save(new User(null, "sebas", encoder.encode("admin"),"admin@mail.to" ,Role.ADMIN));
		userRepo.save(new User(null, "rosa", encoder.encode("1234"), "random@mail.to" ,Role.USER));

	}

}
