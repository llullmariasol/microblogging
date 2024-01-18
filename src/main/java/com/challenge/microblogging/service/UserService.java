package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.UserDTO;
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
                .map(userMapper::mapEntityToDTO).orElse(null);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(String id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            User updatedUser = userMapper.mapDTOToEntity(userDTO);
            updatedUser.setId(existingUser.getId());
            User savedUser = userRepository.save(updatedUser);
            return userMapper.mapEntityToDTO(savedUser);
        } else {
            return null;
        }
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserDTO followUser(String followerId, String followingId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User following = userRepository.findById(followingId).orElse(null);
        if (follower != null && following != null) {
            follower.getFollowing().add(followingId);
            userRepository.save(follower);
            return userMapper.mapEntityToDTO(follower);
        } else {
            return null; // TODO - Manejar el caso cuando los usuarios no existen
        }
    }

    public UserDTO unfollowUser(String followerId, String followingId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User following = userRepository.findById(followingId).orElse(null);
        if (follower != null && following != null) {
            follower.getFollowing().remove(followingId);
            userRepository.save(follower);
            return userMapper.mapEntityToDTO(follower);
        } else {
            return null;
        }
    }
    //TODO - Manejar el caso cuando los usuarios no existen, con excepciones?
}
