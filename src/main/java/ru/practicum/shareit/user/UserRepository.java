package ru.practicum.shareit.user;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(final Long id);

    void deleteEmail(String email);

    Optional<User> getById(final Long id);

    List<User> getAll();

    Long existsEmail(final String email);

    boolean existsId(final Long id);
}
