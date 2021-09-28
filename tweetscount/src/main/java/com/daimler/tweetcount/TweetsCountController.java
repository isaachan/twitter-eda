package com.daimler.tweetcount;

import org.springframework.web.bind.annotation.*;

@RestController
public class TweetsCountController {

    @GetMapping("/tweets/{userId}")
    public void postTweet(@PathVariable long userId) {
    }
}
