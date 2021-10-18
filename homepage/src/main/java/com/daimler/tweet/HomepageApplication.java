package com.daimler.tweet;

import org.springframework.boot.SpringApplication;

import java.util.Collections;

public class HomepageApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HomepageApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
        app.run(args);
    }
}
