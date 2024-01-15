package com.challenge.microblogging.mapper;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.model.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    public static TweetDTO mapEntityToDTO(Tweet tweet) {
        return new TweetDTO(tweet.getId(), tweet.getUserId(), tweet.getContent());
    }

    public static Tweet mapDTOToEntity(TweetDTO tweetDTO) {
        Tweet tweet = new Tweet();
        tweet.setId(tweetDTO.getId());
        tweet.setUserId(tweetDTO.getUserId());
        tweet.setContent(tweetDTO.getContent());
        return tweet;
    }
}
