package com.endacheva.smart_shelf.repository;

import com.endacheva.smart_shelf.model.BorrowRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BorrowRequestRepository extends JpaRepository<BorrowRequest, UUID> {
    List<BorrowRequest> findAllByRequesterId(UUID requesterId);
    List<BorrowRequest> findAllByItemOwnerId(UUID ownerId);
}
