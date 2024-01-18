package com.challenge.microblogging.model;

import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@RequiredArgsConstructor
@Setter
@Getter
@Document(collection = "tweets")
public class Tweet {

    @Id
    private String id;
    private String userId;
    private String content;
    private Date creationDate;
    private boolean deleted = false;
    public void markAsDeleted() {
        this.deleted = true;
    }
}
