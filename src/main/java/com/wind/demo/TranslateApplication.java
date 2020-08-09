package com.wind.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Saint
 */
@SpringBootApplication
@MapperScan("com.wind.demo.dao")
@EntityScan("com.wind.demo.model")
public class TranslateApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslateApplication.class, args);
    }

}
