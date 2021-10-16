package com.daimler.tweet;

import com.daimler.tweet.model.Tweet;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TimelineController {

    @GetMapping("/users/{senderid}/tweets?from={from}&to={to}")
    public List<Tweet> findTweetsBetween(@PathVariable Long senderId,
                                         @RequestParam("from") Optional<String> from,
                                         @RequestParam("to") Optional<String> to) {
        return null;
    }
}
