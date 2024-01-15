package com.challenge.microblogging.mapper;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO mapEntityToDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public User mapDTOToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
