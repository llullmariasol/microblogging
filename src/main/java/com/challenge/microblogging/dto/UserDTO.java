package com.challenge.microblogging.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private Long id;

    //TODO - traducir a ingles
    @NotBlank(message = "El nombre de usuario no puede estar en blanco.")
    @Size(max = 15, message = "El nombre de usuario no puede exceder los 15 caracteres.")
    private String username;

    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String name;

    private Set<Long> following;


    // otros campos
}
