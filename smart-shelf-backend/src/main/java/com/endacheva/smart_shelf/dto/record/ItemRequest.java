package com.endacheva.smart_shelf.dto.record;

import jakarta.validation.constraints.NotBlank;

public record ItemRequest(
        @NotBlank(message = "Название вещи не может быть пустым")
        String title,

        String description
) {}
