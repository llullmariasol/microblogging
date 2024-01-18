package com.challenge.microblogging.repository;

import com.challenge.microblogging.model.Tweet;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {
    @Query(value = "{ 'userId': { $in: ?0 }, 'deleted': false }", sort = "{ 'creationDate': -1 }")
    List<Tweet> findByUserIdInAndDeletedFalseOrderByCreationDateDesc(Set<String> userIds);
}
