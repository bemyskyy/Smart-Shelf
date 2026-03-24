package com.endacheva.smart_shelf.service;

import com.endacheva.smart_shelf.dto.record.ItemRequest;
import com.endacheva.smart_shelf.dto.record.ItemResponse;
import com.endacheva.smart_shelf.model.Item;
import com.endacheva.smart_shelf.model.enums.ItemStatus;
import com.endacheva.smart_shelf.model.User;
import com.endacheva.smart_shelf.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public List<ItemResponse> getAvailableItems(User currentUser) {
        return itemRepository.findByStatusAndOwnerIdNot(ItemStatus.AVAILABLE, currentUser.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getMyItems(User currentUser) {
        return itemRepository.findAllByOwnerId(currentUser.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItemResponse getItemById(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Вещь не найдена"));
        return mapToResponse(item);
    }

    @Transactional
    public ItemResponse createItem(ItemRequest request, User currentUser) {
        Item item = Item.builder()
                .title(request.title())
                .description(request.description())
                .owner(currentUser)
                .status(ItemStatus.AVAILABLE)
                .build();

        return mapToResponse(itemRepository.save(item));
    }

    private ItemResponse mapToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getOwner().getUsername(),
                item.getStatus().name()
        );
    }
}
