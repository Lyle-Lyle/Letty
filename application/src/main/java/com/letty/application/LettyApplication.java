package com.letty.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.letty.application.dao.mapper")
public class LettyApplication {
    public static void main(String[] args) {
        SpringApplication.run(LettyApplication.class, args);
    }
}
