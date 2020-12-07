package com.cx.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cx.demo.mapper")
public class RedisdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisdemoApplication.class, args);
	}

}
