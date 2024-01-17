package com.challenge.microblogging.controller;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Mono<ResponseEntity<UserDTO>> createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO)
                .map(createdUser -> new ResponseEntity<>(createdUser, HttpStatus.CREATED));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> (user != null) ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public Mono<ResponseEntity<List<UserDTO>>> getAllUsers() {
        return userService.getAllUsers()
                .collectList()
                .map(users -> new ResponseEntity<>(users, HttpStatus.OK));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO)
                .map(updatedUser -> (updatedUser != null) ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id)
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    public Mono<ResponseEntity<UserDTO>> followUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        return userService.followUser(followerId, followingId)
                .map(updatedUser -> (updatedUser != null) ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{followerId}/unfollow/{followingId}")
    public Mono<ResponseEntity<UserDTO>> unfollowUser(@PathVariable Long followerId, @PathVariable Long followingId) {
        return userService.unfollowUser(followerId, followingId)
                .map(updatedUser -> (updatedUser != null) ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
