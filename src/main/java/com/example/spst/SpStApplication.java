package com.example.spst;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(basePackages = "com.example.spst.account.dao")
@SpringBootApplication
public class SpStApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpStApplication.class, args);
	}
}
