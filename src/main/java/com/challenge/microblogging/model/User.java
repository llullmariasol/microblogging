package com.challenge.microblogging.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Setter
@Getter
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String name;
    private String bio;
    @CreatedDate
    private Date joinDate;
    private Set<String> following;
    @Indexed(unique = true)
    private String email;
}
