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
@Data
public class UserDTO {
    private Long id;

    //TODO - traducir a ingles
    @NotBlank(message = "El nombre de usuario no puede estar en blanco.")
    @Size(max = 15, message = "El nombre de usuario no puede exceder los 15 caracteres.")
    private String username;

    @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
    private String name;

    private Set<Long> following;

    @Email(message = "El formato del correo electrónico no es válido.") // todo -traducir a ingles
    private String email;

    private String bio;

    private Date joinDate;
}
