package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;
    ItemMapper mapper;
    UserService userService;
    UserMapper userMapper;

    @Override
    public ItemDTO addItem(final Long userId, ItemDTO itemDTO) {
        log.info("идентификатор пользователя: {}", userId);

        isExistUser(userId);
        if (itemDTO.getName() == null || itemDTO.getName().isEmpty() || itemDTO.getDescription() == null ||
                itemDTO.getAvailable() == null) {
            log.error("Ошибка добавления элемента: {}, по идентификатору пользователя: {}", itemDTO, userId);
            throw new BadRequestException("Ошибка добавления элемента");
        }
        itemDTO.setOwner(userMapper.toModel(userService.getById(userId)));
        ItemDTO newItemDTO = mapper.toDTO(itemRepository.addItem(mapper.toModel(itemDTO)));

        log.info("Успешно добавлено: {}", newItemDTO.getId());
        return newItemDTO;
    }

    @Override
    public ItemDTO updateItem(final Long userId, final Long itemId, ItemDTO itemDTO) {
        log.info("Обновление элемента с идентификатором: {} для идентификатора пользователя: {}", itemId, userId);

        isExistUser(userId);
        Item item = mapper.toModel(getItem(itemId));
        if (!item.getOwner().getId().equals(userId)) {
            log.error("Не соответвие ID юзера", userId, itemId);
            throw new NotFoundException("Несовпадение ID");
        }
        if (itemDTO.getName() != null) {
            item.setName(itemDTO.getName());
        }
        if (itemDTO.getDescription() != null) {
            item.setDescription(itemDTO.getDescription());
        }
        if (itemDTO.getAvailable() != null) {
            item.setAvailable(itemDTO.getAvailable());
        }
        itemRepository.updateItem(item);
        log.info("Обновлено", item.getId());
        return mapper.toDTO(item);
    }

    @Override
    public ItemDTO getItem(final Long itemId) {
        final ItemDTO itemDTO = mapper.toDTO(itemRepository.getItem(itemId)
                .orElseThrow(() -> new NotFoundException("Не найдено")));
        log.info("Получение элемента по идентификатору: {}", itemId);
        return itemDTO;
    }

    @Override
    public List<ItemDTO> getItems(final Long userId) {
        log.info("Получение для ID юзера: {}", userId);
        final List<ItemDTO> items = mapper.toListDTO(itemRepository.findAllOwnerId(userId));
        log.info("Всего получено", userId, items.size());
        return items;
    }

    @Override
    public List<ItemDTO> getItemsNameDescription(final String text) {
        log.info("поиск по имени/описанию: {}", text);
        if (text.isEmpty()) {
            log.info("Не нашлось");
            return new ArrayList<>();
        }
        final List<ItemDTO> items = mapper.toListDTO(itemRepository.findAllNameDescription(text));
        return items;
    }

    private void isExistUser(final Long userId) {
        if (!userService.isExistUser(userId)) {
            log.error("Юзер не найден по ID: {}", userId);
            throw new NotFoundException("Юзер не найден по ID");
        }
    }
}
