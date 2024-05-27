package ru.practicum.shareit.item;

import java.util.List;
public interface ItemService {

    ItemDTO addItem(final Long userId, ItemDTO itemDTO);

    ItemDTO updateItem(final Long userId, final Long itemId, ItemDTO itemDTO);

    ItemDTO getItem(final Long itemId);

    List<ItemDTO> getItems(final Long userId);

    List<ItemDTO> getItemsNameDescription(final String text);
}
