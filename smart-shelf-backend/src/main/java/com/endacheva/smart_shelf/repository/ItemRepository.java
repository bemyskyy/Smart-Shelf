package com.endacheva.smart_shelf.repository;

import com.endacheva.smart_shelf.model.Item;
import com.endacheva.smart_shelf.model.enums.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findAllByStatus(ItemStatus status);
    List<Item> findAllByOwnerId(UUID ownerId);
    List<Item> findByStatusAndOwnerIdNot(ItemStatus status, UUID ownerId);
}
