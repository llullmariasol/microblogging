package com.challenge.microblogging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TweetDTO {
    private Long id;
    private Long userId; // creador del tweet
    private String content;
}
