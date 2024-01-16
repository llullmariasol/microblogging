package com.challenge.microblogging.repository;

import com.challenge.microblogging.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    // Recupera los tweets de los usuarios a los que sigue un usuario específico
    List<Tweet> findByUserIdIn(Set<Long> userIds);
}
