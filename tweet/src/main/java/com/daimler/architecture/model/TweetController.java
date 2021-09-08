package com.daimler.architecture.model;

import org.springframework.web.bind.annotation.*;

@RestController
public class TweetController {

    @PostMapping("/tweets/")
    public void postTweet(@RequestBody Tweet tweet) {
        System.out.println("Post tweet " + tweet);
    }

    @DeleteMapping("/tweets/{id}")
    public void deleteTweet(@PathVariable long id) {
        System.out.println("Delete tweet with id " + id);
    }
}
