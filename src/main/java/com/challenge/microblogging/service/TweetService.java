package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.dto.UserDTO;
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
        Tweet tweetToCreate = tweetMapper.mapDTOToEntity(tweetDTO);
        return tweetMapper.mapEntityToDTO(tweetRepository.save(tweetToCreate));
    }

    public TweetDTO getTweetById(String id) {
        return tweetRepository.findById(id)
                .map(tweetMapper::mapEntityToDTO).orElse(null);
    }

    public List<TweetDTO> getAllTweets() {
        return tweetRepository.findAll()
                .stream().map(tweetMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public void deleteTweet(String id) {
        tweetRepository.findById(id).ifPresent(tweet -> tweet.setDeleted(true));
    }

    public List<TweetDTO> getTimelineTweets(String userId) {
        UserDTO user = userService.getUserById(userId);

        if (user != null) {
            Set<String> followingIds = user.getFollowing();
            List<Tweet> timelineTweets = tweetRepository.findByUserIdInAndDeletedFalseOrderByCreationDateDesc(followingIds);
            return timelineTweets.stream()
                    .map(tweetMapper::mapEntityToDTO)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    //TODO - los más recientes obtenerlos de caché?

}
