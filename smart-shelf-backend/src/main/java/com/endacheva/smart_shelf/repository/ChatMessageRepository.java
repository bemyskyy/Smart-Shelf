package com.endacheva.smart_shelf.repository;

import com.endacheva.smart_shelf.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findByRequestIdOrderByCreatedAtAsc(UUID requestId);
}
