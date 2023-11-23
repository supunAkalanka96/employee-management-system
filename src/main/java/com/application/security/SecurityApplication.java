package com.application.security;

import com.application.security.entity.User;
import com.application.security.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecurityApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
//		User adminAccount = userRepository.findByRole(Role.ADMIN);
//
//		if(adminAccount == null){
//			User user = new User();
//
//			user.setFirstname("admin1");
//			user.setLastname("admin1");
//			user.setEmail("admin1@gmail.com");
//			user.setRole(Role.ADMIN);
//			user.setPassword(new BCryptPasswordEncoder().encode("admin1"));
//			userRepository.save(user);
//		}

	}
}
