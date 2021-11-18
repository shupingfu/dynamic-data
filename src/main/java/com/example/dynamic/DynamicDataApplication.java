package com.example.dynamic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.dynamic.mapper")
public class DynamicDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicDataApplication.class, args);
    }

}