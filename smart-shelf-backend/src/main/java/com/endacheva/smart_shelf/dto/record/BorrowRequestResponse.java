package com.endacheva.smart_shelf.dto.record;

import java.util.UUID;

public record BorrowRequestResponse(UUID id, String itemTitle, String requesterUsername, String status) {}