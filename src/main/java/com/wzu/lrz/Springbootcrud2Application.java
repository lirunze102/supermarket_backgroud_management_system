package com.wzu.lrz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
@MapperScan("com.wzu.lrz.dao")
public class Springbootcrud2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springbootcrud2Application.class, args);
    }

}
