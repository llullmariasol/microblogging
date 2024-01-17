package com.challenge.microblogging.controller;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.service.TweetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tweets")
@Validated
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @PostMapping
    public ResponseEntity<Mono<TweetDTO>> createTweet(@Valid @RequestBody TweetDTO tweetDTO) {
        Mono<TweetDTO> createdTweet = tweetService.createTweet(tweetDTO);
        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<TweetDTO>> getTweetById(@PathVariable String id) {
        Mono<TweetDTO> tweet = tweetService.getTweetById(id);
        return (tweet != null) ? new ResponseEntity<>(tweet, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Flux<TweetDTO>> getAllTweets() {
        Flux<TweetDTO> tweets = tweetService.getAllTweets();
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<TweetDTO>> updateTweet(@PathVariable String id, @RequestBody TweetDTO tweetDTO) {
        Mono<TweetDTO> updatedTweet = tweetService.updateTweet(id, tweetDTO);
        return (updatedTweet != null) ? new ResponseEntity<>(updatedTweet, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteTweet(@PathVariable String id) {
        tweetService.deleteTweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/timeline/{userId}")
    public ResponseEntity<Flux<TweetDTO>> getTimeline(@PathVariable Long userId) {
        Flux<TweetDTO> timelineTweets = tweetService.getTimelineTweets(userId);
        return ResponseEntity.ok(timelineTweets);
    }
}
