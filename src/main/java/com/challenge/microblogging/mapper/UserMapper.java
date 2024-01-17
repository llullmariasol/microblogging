package com.challenge.microblogging.mapper;

import com.challenge.microblogging.dto.UserDTO;
import com.challenge.microblogging.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    @Mapping(source = "creationDate", target = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    UserDTO mapEntityToDTO(User user);

    @Mapping(source = "creationDate", target = "creationDate", dateFormat = "yyyy-MM-dd HH:mm:ss")
    User mapDTOToEntity(UserDTO userDTO);
}
