package com.challenge.microblogging.repository;

import com.challenge.microblogging.model.Tweet;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {

    @Query(value = "{ 'userId': { $in: ?0 }, 'deleted': false }", sort = "{ 'creationDate': -1 }")
    Flux<Tweet> findByUserIdInAndDeletedFalseOrderByCreationDateDesc(Set<Long> userIds);
}
