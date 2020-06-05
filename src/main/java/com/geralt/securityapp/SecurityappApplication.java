package com.geralt.securityapp;

import com.geralt.securityapp.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@SpringBootApplication
public class SecurityappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityappApplication.class, args);
	}

}
