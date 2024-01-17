package com.challenge.microblogging.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class TweetDTO {
    private Long id;
    private Long userId; // creador del tweet

    @NotBlank(message = "The tweet content cannot be blank.")
    @Size(max = 280, message = "The tweet content cannot exceed 280 characters.")
    private String content;

    private Date creationDate;

    private boolean deleted;
}
