package com.challenge.microblogging.mapper;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "joinDate", target = "joinDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    UserDTO mapEntityToDTO(User user);

    @Mapping(source = "joinDate", target = "joinDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    User mapDTOToEntity(UserDTO userDTO);

}
