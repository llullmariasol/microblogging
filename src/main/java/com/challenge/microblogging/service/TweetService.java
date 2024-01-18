package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.exception.ResourceNotFoundException;
import com.challenge.microblogging.mapper.TweetMapper;
import com.challenge.microblogging.model.Tweet;
import com.challenge.microblogging.repository.TweetRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TweetMapper tweetMapper;

    @Autowired
    private UserService userService;

    public TweetDTO createTweet(@Valid TweetDTO tweetDTO) {
        userService.getUserById(tweetDTO.getUserId());
        Tweet tweetToCreate = tweetMapper.mapDTOToEntity(tweetDTO);
        return tweetMapper.mapEntityToDTO(tweetRepository.save(tweetToCreate));
    }

    public TweetDTO getTweetById(String id) {
        return tweetRepository.findById(id)
                .map(tweetMapper::mapEntityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id " + id));
    }

    public List<TweetDTO> getAllTweets() {
        return tweetRepository.findAll()
                .stream().map(tweetMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public void deleteTweet(String id) {
        Tweet tweetToUpdate = tweetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet not found with id " + id));
        tweetToUpdate.markAsDeleted();
        tweetRepository.save(tweetToUpdate);
    }

    public List<TweetDTO> getTimelineTweets(String userId) {
        UserDTO user = userService.getUserById(userId);

        Set<String> followingIds = user.getFollowing();
        List<Tweet> timelineTweets = tweetRepository.findByUserIdInAndDeletedFalseOrderByCreationDateDesc(followingIds);
        return timelineTweets.stream()
                .map(tweetMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
