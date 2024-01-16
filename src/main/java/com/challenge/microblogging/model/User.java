package com.challenge.microblogging.model;

import jakarta.validation.constraints.Email;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 15)
    private String username;

    @Column(length = 50)
    private String name;

    @ElementCollection
    private Set<Long> following;

    @Email(message = "El formato del correo electrónico no es válido.")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Tweet> tweets;

    //TODO - agregar relaciones
}
