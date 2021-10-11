package com.daimler.tweet;

import org.springframework.boot.SpringApplication;

import java.util.Collections;

public class TimelineApplication {
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TimelineApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }
}
