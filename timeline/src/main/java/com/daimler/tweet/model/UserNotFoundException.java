package com.daimler.tweet.model;

public class UserNotFoundException extends RuntimeException {
    private Long userId;

    public UserNotFoundException(Long userId) {
        this.userId = userId;
    }
}
