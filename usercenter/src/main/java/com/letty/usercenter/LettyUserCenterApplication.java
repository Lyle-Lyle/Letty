package com.letty.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.letty.usercenter.dao.mapper")
public class LettyUserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(LettyUserCenterApplication.class, args);
    }
}
