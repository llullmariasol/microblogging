package com.challenge.microblogging.controller;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.service.TweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(TweetController.class)
@AutoConfigureMockMvc
@AutoConfigureDataMongo
public class TweetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TweetService tweetService;

    @Test
    public void testCreateTweet() throws Exception {
        when(tweetService.createTweet(any(TweetDTO.class))).thenReturn(getTweetDTO());

        mockMvc.perform(post("/tweets/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(getTweetDTO())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testGetTweetById() throws Exception {
        when(tweetService.getTweetById("1")).thenReturn(getTweetDTO());

        mockMvc.perform(get("/tweets/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGetAllTweets() throws Exception {
        // Arrange
        List<TweetDTO> tweets = Arrays.asList(
                getTweetDTO(),
                getTweetDTO()
        );
        when(tweetService.getAllTweets()).thenReturn(tweets);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/tweets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(tweets.size()));
    }

    @Test
    void testDeleteTweet() throws Exception {
        // Arrange
        String tweetId = "1";

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/tweets/{id}", tweetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Verify that the service method was called
        verify(tweetService, times(1)).deleteTweet(anyString());
    }

    @Test
    void testGetTimeline() throws Exception {
        // Arrange
        String userId = "User1";
        int page = 0;
        int size = 10;
        List<TweetDTO> timelineTweets = Arrays.asList(
                getTweetDTO(),
                getTweetDTO()
        );
        when(tweetService.getTimelineTweets(eq(userId), any())).thenReturn(timelineTweets);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/tweets/timeline/{userId}", userId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(timelineTweets.size()));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TweetDTO getTweetDTO() {
        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setId("1");
        tweetDTO.setUserId("1");
        tweetDTO.setContent("Test tweet");
        return tweetDTO;
    }
}
