package com.challenge.microblogging.controller;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.exception.ResourceNotFoundException;
import com.challenge.microblogging.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/new")
    public ResponseEntity<UserDTO> createUser(@Valid  @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            if (userDTO == null) {
                throw new ResourceNotFoundException("User not found with id " + id);
            }
            return new ResponseEntity<>(userDTO, HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            String message = "Resource not found: " + e.getMessage() + " (id=" + id + ")";

            Map<String, Object> body = new HashMap<>();
            body.put("message", message);

            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity<UserDTO> followUser(@PathVariable String followerId, @PathVariable String followingId) {
        UserDTO updatedUser = userService.followUser(followerId, followingId);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity<UserDTO> unfollowUser(@PathVariable String followerId, @PathVariable String followingId) {
        UserDTO updatedUser = userService.unfollowUser(followerId, followingId);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
