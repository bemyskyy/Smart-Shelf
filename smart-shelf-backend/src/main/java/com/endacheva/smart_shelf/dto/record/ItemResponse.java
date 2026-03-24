package com.endacheva.smart_shelf.dto.record;

import java.util.UUID;

public record ItemResponse(UUID id, String title, String description, String ownerUsername, String status) {}