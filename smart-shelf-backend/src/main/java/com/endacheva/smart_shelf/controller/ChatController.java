package com.endacheva.smart_shelf.controller;

import com.endacheva.smart_shelf.dto.record.ChatMessageResponse;
import com.endacheva.smart_shelf.dto.record.ChatSendRequest;
import com.endacheva.smart_shelf.model.User;
import com.endacheva.smart_shelf.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests/{requestId}/messages")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
            @PathVariable UUID requestId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(chatService.getMessages(requestId, currentUser));
    }

    @PostMapping
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @PathVariable UUID requestId,
            @Valid @RequestBody ChatSendRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(chatService.sendMessage(requestId, request.content(), currentUser));
    }
}
