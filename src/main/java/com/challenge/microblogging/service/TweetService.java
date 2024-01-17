package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.mapper.TweetMapper;
import com.challenge.microblogging.model.Tweet;
import com.challenge.microblogging.model.User;
import com.challenge.microblogging.repository.TweetRepository;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    public Mono<TweetDTO> createTweet(@Valid TweetDTO tweetDTO) {
        Tweet tweetToCreate = tweetMapper.mapDTOToEntity(tweetDTO);
        return tweetRepository.save(tweetToCreate)
                .map(tweetMapper::mapEntityToDTO);
    }

    public Mono<TweetDTO> getTweetById(String id) {
        return tweetRepository.findById(id)
                .map(tweetMapper::mapEntityToDTO);
    }

    public Flux<TweetDTO> getAllTweets() {
        return tweetRepository.findAll()
                .map(tweetMapper::mapEntityToDTO);
    }

    public Mono<TweetDTO> updateTweet(String id, TweetDTO tweetDTO) {
        return tweetRepository.findById(id)
                .flatMap(existingTweet -> {
                    Tweet updatedTweet = tweetMapper.mapDTOToEntity(tweetDTO);
                    updatedTweet.setId(existingTweet.getId());
                    return tweetRepository.save(updatedTweet)
                            .map(tweetMapper::mapEntityToDTO);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<Void> deleteTweet(String id) {
        return tweetRepository.deleteById(id);
    }

    public Flux<TweetDTO> getTimelineTweets(String userId) {
        return userService.getUserById(userId)
                .flatMapMany(user -> {
                    Set<Long> followingIds = user.getFollowing();
                    return tweetRepository.findByUserIdInAndDeletedFalseOrderByCreationDateDesc(followingIds)
                            .map(tweetMapper::mapEntityToDTO);
                })
                .switchIfEmpty(Flux.empty());
    }
    //TODO - los más recientes obtenerlos de caché?

}
