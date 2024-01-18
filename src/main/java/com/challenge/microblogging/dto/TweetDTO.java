package com.challenge.microblogging.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TweetDTO {
    private String id;
    private Long userId;

    @NotBlank(message = "The tweet content cannot be blank.")
    @Size(max = 280, message = "The tweet content cannot exceed 280 characters.")
    private String content;

    private Date creationDate;

    private boolean deleted;
}
