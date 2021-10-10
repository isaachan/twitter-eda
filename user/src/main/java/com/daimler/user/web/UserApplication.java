package com.daimler.user.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UserApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }
}
