package com.openbootcamp.ejercicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openbootcamp.ejercicios.Model.Entities.User;
import com.openbootcamp.ejercicios.Model.Repository.UserRepository;

@SpringBootApplication
public class EjerciciosApplication {


	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(EjerciciosApplication.class, args);

		UserRepository userRepo = app.getBean(UserRepository.class);
		PasswordEncoder encoder = app.getBean(PasswordEncoder.class);

		User user = new User(null, "sebas", encoder.encode("admin"));	
		User user1 = new User(null, "rosa", encoder.encode("1234"));		

		userRepo.save(user);
		userRepo.save(user1);

	}

}
