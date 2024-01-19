package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.exception.ResourceNotFoundException;
import com.challenge.microblogging.mapper.UserMapper;
import com.challenge.microblogging.model.User;
import com.challenge.microblogging.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserMapper userMapper;

    @Test
    void testCreateUser() {
        String userId = "testUserId";
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("testUser");
        userDTO.setEmail("test@example.com");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("testUser");
        existingUser.setEmail("test@example.com");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userMapper.mapDTOToEntity(userDTO)).thenReturn(existingUser);
        Mockito.when(userRepository.save(existingUser)).thenReturn(existingUser);
        Mockito.when(userMapper.mapEntityToDTO(existingUser)).thenReturn(userDTO);

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser.getId());
        assertEquals("testUser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());
    }

    @Test
    void testGetUserById() {
        String userId = "testUserId";
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("testUser");
        userDTO.setEmail("test@example.com");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("testUser");
        existingUser.setEmail("test@example.com");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userMapper.mapDTOToEntity(userDTO)).thenReturn(existingUser);
        Mockito.when(userRepository.save(existingUser)).thenReturn(existingUser);
        Mockito.when(userMapper.mapEntityToDTO(existingUser)).thenReturn(userDTO);

        UserDTO createdUser = userService.createUser(userDTO);

        UserDTO retrievedUser = userService.getUserById(createdUser.getId());

        assertNotNull(retrievedUser);
        assertEquals(createdUser.getId(), retrievedUser.getId());
        assertEquals("testUser", retrievedUser.getUsername());
        assertEquals("test@example.com", retrievedUser.getEmail());
    }

    @Test
    void testGetAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), userService.getAllUsers());
    }

    @Test
    void testUpdateUser() {
        String userId = "testUserId";
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setUsername("updatedUsername");
        userDTO.setEmail("updated@example.com");

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setEmail("old@example.com");

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        Mockito.when(userMapper.mapDTOToEntity(userDTO)).thenReturn(existingUser);
        Mockito.when(userRepository.save(existingUser)).thenReturn(existingUser);
        Mockito.when(userMapper.mapEntityToDTO(existingUser)).thenReturn(userDTO);

        UserDTO updatedUser = userService.updateUser(userId, userDTO);

        assertEquals(userId, updatedUser.getId());
        assertEquals("updatedUsername", updatedUser.getUsername());
        assertEquals("updated@example.com", updatedUser.getEmail());
    }

    @Test
    void testUpdateUser_ThrowsResourceNotFoundException() {
        String userId = "nonexistentUserId";
        UserDTO userDTO = new UserDTO();

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(userId, userDTO));
    }

    @Test
    void testFollowUser() {
        String followerId = "followerUserId";
        String followingId = "followingUserId";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(followerId);

        User follower = new User();
        follower.setId(followerId);
        Set<String> following = new HashSet<String>();
        following.add(followingId);
        follower.setFollowing(following);
        userDTO.setFollowing(following);

        Mockito.when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        Mockito.when(userRepository.findById(followingId)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(follower);
        Mockito.when(userMapper.mapEntityToDTO(follower)).thenReturn(userDTO);

        UserDTO updatedUser = userService.followUser(followerId, followingId);

        assertEquals(followerId, updatedUser.getId());
        assertTrue(updatedUser.getFollowing().contains(followingId));
    }

    @Test
    void testFollowUser_ThrowsResourceNotFoundException() {
        String followerId = "nonexistentFollowerId";
        String followingId = "followingUserId";

        Mockito.when(userRepository.findById(followerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.followUser(followerId, followingId));
    }

    @Test
    void testUnfollowUser() {
        String followerId = "followerUserId";
        String followingId = "followingUserId";

        UserDTO userDTO = new UserDTO();
        userDTO.setId(followerId);

        User follower = new User();
        follower.setId(followerId);
        Set<String> following = new HashSet<String>();
        following.add(followingId);
        follower.setFollowing(following);
        userDTO.setFollowing(following);

        Mockito.when(userRepository.findById(followerId)).thenReturn(Optional.of(follower));
        Mockito.when(userRepository.findById(followingId)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(follower);
        Mockito.when(userMapper.mapEntityToDTO(follower)).thenReturn(userDTO);

        userRepository.save(follower);

        UserDTO updatedUser = userService.unfollowUser(followerId, followingId);

        assertEquals(followerId, updatedUser.getId());
        assertTrue(updatedUser.getFollowing().isEmpty());
    }

    @Test
    void testUnfollowUser_ThrowsResourceNotFoundException() {
        String followerId = "nonexistentFollowerId";
        String followingId = "followingUserId";

        Mockito.when(userRepository.findById(followerId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.unfollowUser(followerId, followingId));
    }
}
