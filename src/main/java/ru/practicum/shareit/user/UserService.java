package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    UserDTO addUser(UserDTO userDTO);

    UserDTO updateUser(final Long id, UserDTO user);

    UserDTO getById(final Long id);

    void deleteUser(final Long id);

    List<UserDTO> getAll();

    boolean isExistUser(final Long id);
}
