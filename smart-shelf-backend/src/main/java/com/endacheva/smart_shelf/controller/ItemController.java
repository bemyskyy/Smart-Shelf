package com.endacheva.smart_shelf.controller;

import com.endacheva.smart_shelf.dto.record.ItemRequest;
import com.endacheva.smart_shelf.dto.record.ItemResponse;
import com.endacheva.smart_shelf.model.User;
import com.endacheva.smart_shelf.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAvailableItems(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(itemService.getAvailableItems(currentUser));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ItemResponse>> getMyItems(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(itemService.getMyItems(currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable UUID id) {
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@Valid  @RequestBody ItemRequest request,
                                                   @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(itemService.createItem(request, currentUser));
    }
}
