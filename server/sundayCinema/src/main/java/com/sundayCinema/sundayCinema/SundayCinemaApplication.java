package com.sundayCinema.sundayCinema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableJpaAuditing

@SpringBootApplication
public class SundayCinemaApplication {

	public static void main(String[] args) {SpringApplication.run(SundayCinemaApplication.class, args);}

}
