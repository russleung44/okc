package com.okc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication()
@MapperScan(basePackages = {"com.okc.mgb.model.mapper"})
public class OkcApplication {

    public static void main(String[] args) {
        SpringApplication.run(OkcApplication.class, args);
    }

}
