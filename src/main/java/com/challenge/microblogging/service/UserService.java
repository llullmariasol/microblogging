package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.exception.ResourceNotFoundException;
import com.challenge.microblogging.mapper.UserMapper;
import com.challenge.microblogging.model.User;
import com.challenge.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO) {
        User userToCreate = userMapper.mapDTOToEntity(userDTO);
        return userMapper.mapEntityToDTO(userRepository.save(userToCreate));
    }

    public UserDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::mapEntityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(String id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        User updatedUser = userMapper.mapDTOToEntity(userDTO);
        updatedUser.setId(existingUser.getId());
        User savedUser = userRepository.save(updatedUser);
        return userMapper.mapEntityToDTO(savedUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserDTO followUser(String followerId, String followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new ResourceNotFoundException("Follower not found with id " + followerId));
        userRepository.findById(followingId)
                .orElseThrow(() -> new ResourceNotFoundException("User to follow not found with id " + followingId));
        follower.getFollowing().add(followingId);
        userRepository.save(follower);
        return userMapper.mapEntityToDTO(follower);
    }

    public UserDTO unfollowUser(String followerId, String followingId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new ResourceNotFoundException("Follower not found with id " + followerId));
        userRepository.findById(followingId)
                .orElseThrow(() -> new ResourceNotFoundException("User to follow not found with id " + followingId));
        follower.getFollowing().remove(followingId);
        userRepository.save(follower);
        return userMapper.mapEntityToDTO(follower);
    }
}
