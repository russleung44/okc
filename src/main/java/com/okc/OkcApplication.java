package com.okc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
@MapperScan(basePackages = {"com.okc.mgb.mapper"})
public class OkcApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkcApplication.class, args);
    }

}
