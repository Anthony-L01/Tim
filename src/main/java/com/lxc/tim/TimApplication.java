package com.lxc.tim;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class TimApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimApplication.class, args);
    }

}
