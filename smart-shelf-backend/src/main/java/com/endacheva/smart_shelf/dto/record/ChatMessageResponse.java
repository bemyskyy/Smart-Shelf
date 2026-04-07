package com.endacheva.smart_shelf.dto.record;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatMessageResponse(
        UUID id,
        String senderUsername,
        String content,
        LocalDateTime createdAt,
        boolean isMine
) {}
