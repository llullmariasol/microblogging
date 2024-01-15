package com.challenge.microblogging.controller;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @PostMapping
    public ResponseEntity<TweetDTO> createTweet(@RequestBody TweetDTO tweetDTO) {
        TweetDTO createdTweet = tweetService.createTweet(tweetDTO);
        return new ResponseEntity<>(createdTweet, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetDTO> getTweetById(@PathVariable Long id) {
        TweetDTO tweet = tweetService.getTweetById(id);
        return (tweet != null) ? new ResponseEntity<>(tweet, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<TweetDTO>> getAllTweets() {
        List<TweetDTO> tweets = tweetService.getAllTweets();
        return new ResponseEntity<>(tweets, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TweetDTO> updateTweet(@PathVariable Long id, @RequestBody TweetDTO tweetDTO) {
        TweetDTO updatedTweet = tweetService.updateTweet(id, tweetDTO);
        return (updatedTweet != null) ? new ResponseEntity<>(updatedTweet, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable Long id) {
        tweetService.deleteTweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Otros endpoints relacionados con tweets
}
