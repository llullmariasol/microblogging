package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.mapper.UserMapper;
import com.challenge.microblogging.model.User;
import com.challenge.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User userToCreate = userMapper.mapDTOToEntity(userDTO);
        User newUser = userRepository.save(userToCreate);
        return userMapper.mapEntityToDTO(newUser);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return (user != null) ? userMapper.mapEntityToDTO(user) : null;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::mapEntityToDTO).collect(Collectors.toList());
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
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

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public UserDTO followUser(Long followerId, Long followingId) {
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

    @Transactional
    public UserDTO unfollowUser(Long followerId, Long followingId) {
        User follower = userRepository.findById(followerId).orElse(null);
        User following = userRepository.findById(followingId).orElse(null);

        if (follower != null && following != null) {
            follower.getFollowing().remove(followingId);
            userRepository.save(follower);
            return userMapper.mapEntityToDTO(follower);
        } else {
            return null; //TODO - Manejar el caso cuando los usuarios no existen, con excepciones?
        }
    }

}
