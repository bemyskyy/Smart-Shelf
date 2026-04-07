package com.endacheva.smart_shelf.dto.record;

import jakarta.validation.constraints.NotBlank;

public record ChatSendRequest(
        @NotBlank(message = "Сообщение не может быть пустым")
        String content
) {}
