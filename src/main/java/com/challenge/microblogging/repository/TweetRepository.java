package com.challenge.microblogging.repository;

import com.challenge.microblogging.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    // Puedes agregar consultas personalizadas si es necesario.
    // Spring Data JPA generará automáticamente las consultas basadas en el nombre del método.
}
