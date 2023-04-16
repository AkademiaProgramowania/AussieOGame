package com.aussieogame.backend.mapper;

import com.aussieogame.backend.model.dao.impl.User;
import com.aussieogame.backend.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
}
