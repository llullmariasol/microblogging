package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.exception.ResourceNotFoundException;
import com.challenge.microblogging.mapper.TweetMapper;
import com.challenge.microblogging.model.Tweet;
import com.challenge.microblogging.repository.TweetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class TweetServiceTest {

    @MockBean
    private TweetRepository tweetRepository;

    @MockBean
    private TweetMapper tweetMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    @Test
    void testCreateTweet() {
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setId("1");
        tweetDTO.setUserId("userId");
        tweetDTO.setContent("Test tweet");

        UserDTO userDTO = new UserDTO();
        userDTO.setId("userId");

        Mockito.when(userService.getUserById("userId")).thenReturn(userDTO);
        Mockito.when(tweetMapper.mapDTOToEntity(tweetDTO)).thenReturn(new Tweet());
        Mockito.when(tweetMapper.mapEntityToDTO(any(Tweet.class))).thenReturn(tweetDTO);
        Mockito.when(tweetRepository.save(any(Tweet.class))).thenReturn(new Tweet());

        TweetDTO createdTweet = tweetService.createTweet(tweetDTO);

        assertNotNull(createdTweet);
        assertEquals("Test tweet", createdTweet.getContent());
    }

    @Test
    void testCreateTweet_ThrowsResourceNotFoundException() {
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setUserId("nonexistentUserId");

        Mockito.when(userService.getUserById("nonexistentUserId")).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> tweetService.createTweet(tweetDTO));
    }

    @Test
    void testGetTweetById() {
        String tweetId = "testTweetId";
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setId(tweetId);

        Mockito.when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(new Tweet()));
        Mockito.when(tweetMapper.mapEntityToDTO(any(Tweet.class))).thenReturn(tweetDTO);

        TweetDTO retrievedTweet = tweetService.getTweetById(tweetId);

        assertNotNull(retrievedTweet);
        assertEquals(tweetId, retrievedTweet.getId());
    }

    @Test
    void testGetTweetById_ThrowsResourceNotFoundException() {
        String tweetId = "nonexistentTweetId";

        Mockito.when(tweetRepository.findById(tweetId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tweetService.getTweetById(tweetId));
    }

    @Test
    void testGetAllTweets() {
        Mockito.when(tweetRepository.findAll()).thenReturn(Collections.singletonList(new Tweet()));
        Mockito.when(tweetMapper.mapEntityToDTO(any(Tweet.class))).thenReturn(new TweetDTO());

        List<TweetDTO> allTweets = tweetService.getAllTweets();

        assertFalse(allTweets.isEmpty());
    }

    @Test
    void testDeleteTweet() {
        String tweetId = "testTweetId";
        Tweet tweetToDelete = new Tweet();
        tweetToDelete.setId(tweetId);

        Mockito.when(tweetRepository.findById(tweetId)).thenReturn(Optional.of(tweetToDelete));
        Mockito.when(tweetMapper.mapEntityToDTO(any(Tweet.class))).thenReturn(new TweetDTO());

        tweetService.deleteTweet(tweetId);

        assertTrue(tweetToDelete.isDeleted());
        Mockito.verify(tweetRepository, Mockito.times(1)).save(tweetToDelete);
    }

    @Test
    void testDeleteTweet_ThrowsResourceNotFoundException() {
        String tweetId = "nonexistentTweetId";

        Mockito.when(tweetRepository.findById(tweetId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tweetService.deleteTweet(tweetId));
    }

    @Test
    void testGetTimelineTweets() {
        String userId = "testUserId";
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);

        Mockito.when(userService.getUserById(userId)).thenReturn(userDTO);
        Mockito.when(tweetRepository.findByUserIdInAndDeletedFalseOrderByCreationDateDesc(any(), any(Pageable.class)))
                .thenReturn(Collections.singletonList(new Tweet()));
        Mockito.when(tweetMapper.mapEntityToDTO(any(Tweet.class))).thenReturn(new TweetDTO());

        List<TweetDTO> timelineTweets = tweetService.getTimelineTweets(userId, Pageable.unpaged());

        assertFalse(timelineTweets.isEmpty());
    }

}
