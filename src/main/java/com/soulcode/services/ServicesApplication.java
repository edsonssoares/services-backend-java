package com.soulcode.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableCaching // comentar caso o redis não esteja ok ainda
@SpringBootApplication
public class ServicesApplication {

	public static void main(String[] args) {

        SpringApplication.run(ServicesApplication.class, args);

//		System.out.println(new BCryptPasswordEncoder().encode("bahia")); para gerar a senha/hash. Senha gerada: $2a$10$/YAFPZ1NhSV7Vk.YqujDxe0z3akk2RfLf.hnX/D3KQLnZQIR8ZXpK

	}



}
