package ru.practicum.shareit.user;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toModel(UserDTO userDTO);

    List<UserDTO> toDTOList(List<User> modelList);
}