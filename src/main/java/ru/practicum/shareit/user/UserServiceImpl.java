package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.BadValidException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper mapper;


    @Override
    public UserDTO addUser(UserDTO userDTO) {
        checkEmail(userDTO);
        final UserDTO newUserDTO = mapper.toDTO(userRepository.addUser(mapper.toModel(userDTO)));
        return newUserDTO;
    }

    @Override
    public UserDTO updateUser(final Long id, UserDTO userDTO) {
        userDTO.setId(id);
        User user = userRepository.getById(id).orElseThrow(() -> new NotFoundException("Юзер не найден по ID " + id));
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getEmail() != null) {
            checkUserIdEmail(userDTO);
            userRepository.deleteEmail(user.getEmail());
            user.setEmail(userDTO.getEmail());
        }
        userRepository.updateUser(user);
        return mapper.toDTO(user);
    }

    @Override
    public UserDTO getById(final Long id) throws NotFoundException {
        final UserDTO userDTO = mapper.toDTO(userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("Юзер не найден по ID " + id)));
        return userDTO;
    }

    @Transactional
    @Override
    public void deleteUser(final Long id) throws NotFoundException {
        userRepository.deleteUser(id);
    }

    @Override
    public List<UserDTO> getAll() {
        final List<UserDTO> users = mapper.toDTOList(userRepository.getAll());
        return users;
    }

    @Override
    public boolean isExistUser(final Long id) {
        final boolean exists = userRepository.existsId(id);
        return exists;
    }

    private void checkEmail(final UserDTO userDTO) {
        if (userRepository.existsEmail(userDTO.getEmail()) != null) {
            throw new BadValidException("у пользователя email");
        }
    }

    private void checkUserIdEmail(final UserDTO userDTO) {
        final Long userIdByEmail = userRepository.existsEmail(userDTO.getEmail());
        if (userIdByEmail != null) {
            if (!userIdByEmail.equals(userDTO.getId())) {
                throw new BadValidException("email занят");
            }
        }
    }
}
