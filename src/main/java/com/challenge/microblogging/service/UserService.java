package com.challenge.microblogging.service;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.mapper.UserMapper;
import com.challenge.microblogging.model.User;
import com.challenge.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public Mono<UserDTO> createUser(UserDTO userDTO) {
        User userToCreate = userMapper.mapDTOToEntity(userDTO);
        return userRepository.save(userToCreate)
                .map(userMapper::mapEntityToDTO);
    }

    public Mono<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::mapEntityToDTO);
    }

    public Flux<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .map(userMapper::mapEntityToDTO);
    }

    public Mono<UserDTO> updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    User updatedUser = userMapper.mapDTOToEntity(userDTO);
                    updatedUser.setId(existingUser.getId());
                    return userRepository.save(updatedUser);
                })
                .map(userMapper::mapEntityToDTO);
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    public Mono<UserDTO> followUser(Long followerId, Long followingId) {
        return userRepository.findById(followerId)
                .flatMap(follower -> {
                    return userRepository.findById(followingId)
                            .flatMap(following -> {
                                follower.getFollowing().add(followingId);
                                return userRepository.save(follower);
                            });
                })
                .map(userMapper::mapEntityToDTO);
    }

    public Mono<UserDTO> unfollowUser(Long followerId, Long followingId) {
        return userRepository.findById(followerId)
                .flatMap(follower -> {
                    return userRepository.findById(followingId)
                            .flatMap(following -> {
                                follower.getFollowing().remove(followingId);
                                return userRepository.save(follower);
                            });
                })
                .map(userMapper::mapEntityToDTO);
    }

    //TODO - Manejar el caso cuando los usuarios no existen, con excepciones?
}
