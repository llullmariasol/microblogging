package com.challenge.microblogging.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private String id;
    @NotBlank(message = "The username cannot be blank.")
    @Size(max = 15, message = "The username cannot exceed 15 characters.")
    private String username;
    @Size(max = 50, message = "The name cannot exceed 50 characters.")
    private String name;
    private Set<String> following;
    private Set<String> followers;
    @Email(message = "The email format is not valid.")
    private String email;
    private String bio;
    private Date joinDate;
}
