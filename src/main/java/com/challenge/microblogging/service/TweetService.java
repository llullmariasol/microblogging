package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.mapper.TweetMapper;
import com.challenge.microblogging.model.Tweet;
import com.challenge.microblogging.repository.TweetRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetMapper tweetMapper;

    @Transactional
    public TweetDTO createTweet(@Valid TweetDTO tweetDTO) {
        Tweet tweetToCreate = tweetMapper.mapDTOToEntity(tweetDTO);
        Tweet newTweet = tweetRepository.save(tweetToCreate);
        return tweetMapper.mapEntityToDTO(newTweet);
    }

    public TweetDTO getTweetById(Long id) {
        Tweet tweet = tweetRepository.findById(id).orElse(null);
        return (tweet != null) ? tweetMapper.mapEntityToDTO(tweet) : null;
    }

    public List<TweetDTO> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findAll();
        return tweets.stream().map(tweetMapper::mapEntityToDTO).collect(Collectors.toList());
    }

    public TweetDTO updateTweet(Long id, TweetDTO tweetDTO) {
        Tweet existingTweet = tweetRepository.findById(id).orElse(null);

        if (existingTweet != null) {
            Tweet updatedTweet = tweetMapper.mapDTOToEntity(tweetDTO);
            updatedTweet.setId(existingTweet.getId());
            Tweet savedTweet = tweetRepository.save(updatedTweet);
            return tweetMapper.mapEntityToDTO(savedTweet);
        } else {
            return null;
        }
    }

    public void deleteTweet(Long id) {
        tweetRepository.deleteById(id);
    }

    // Otros métodos relacionados con tweets
}
