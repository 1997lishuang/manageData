package com.lishuang.display;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.lishuang.display")
public class DisplayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DisplayApplication.class, args);
    }

}
