package com.challenge.microblogging.mapper;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.model.Tweet;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    public TweetDTO mapEntityToDTO(Tweet tweet) {
        return new TweetDTO(tweet.getId(), tweet.getUserId(), tweet.getContent());
    }

    public Tweet mapDTOToEntity(TweetDTO tweetDTO) {
        return new Tweet(tweetDTO.getId(), tweetDTO.getUserId(), tweetDTO.getContent());
    }
}
