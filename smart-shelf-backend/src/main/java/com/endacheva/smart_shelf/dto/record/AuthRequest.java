package com.endacheva.smart_shelf.dto.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(
        @NotBlank(message = "Имя пользователя обязательно")
        String username,

        @NotBlank(message = "Пароль обязателен")
        @Size(min = 4, message = "Пароль должен быть не менее 4 символов")
        String password
){}
