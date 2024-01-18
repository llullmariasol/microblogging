package com.challenge.microblogging.controller;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.service.TweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@Validated
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @PostMapping("/new")
    public ResponseEntity<TweetDTO> createTweet(@Valid @RequestBody TweetDTO tweetDTO) {
        TweetDTO createdTweet = tweetService.createTweet(tweetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTweet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetDTO> getTweetById(@PathVariable String id) {
        TweetDTO tweet = tweetService.getTweetById(id);
        return ResponseEntity.ok(tweet);
    }

    @GetMapping
    public ResponseEntity<List<TweetDTO>> getAllTweets() {
        List<TweetDTO> tweets = tweetService.getAllTweets();
        return ResponseEntity.ok(tweets);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable String id) {
        tweetService.deleteTweet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/timeline/{userId}")
    public ResponseEntity<List<TweetDTO>> getTimeline(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<TweetDTO> timelineTweets = tweetService.getTimelineTweets(userId, pageable);
        return ResponseEntity.ok(timelineTweets);
    }
}
