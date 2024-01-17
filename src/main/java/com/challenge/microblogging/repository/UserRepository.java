package com.challenge.microblogging.repository;

import com.challenge.microblogging.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {
    Mono<User> findByName(String name);

    Mono<User> findByEmail(String email);
}
