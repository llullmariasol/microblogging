package com.challenge.microblogging.mapper;

import com.challenge.microblogging.dto.TweetDTO;
import com.challenge.microblogging.model.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TweetMapper {
    @Mapping(source = "creationDate", target = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    TweetDTO mapEntityToDTO(Tweet tweet);

    @Mapping(source = "creationDate", target = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    Tweet mapDTOToEntity(TweetDTO tweetDTO);
}
