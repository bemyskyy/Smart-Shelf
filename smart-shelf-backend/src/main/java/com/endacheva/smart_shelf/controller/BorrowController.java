package com.endacheva.smart_shelf.controller;

import com.endacheva.smart_shelf.dto.record.BorrowRequestCreate;
import com.endacheva.smart_shelf.dto.record.BorrowRequestResponse;
import com.endacheva.smart_shelf.model.enums.RequestStatus;
import com.endacheva.smart_shelf.model.User;
import com.endacheva.smart_shelf.service.BorrowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping
    public ResponseEntity<BorrowRequestResponse> createRequest(@Valid @RequestBody BorrowRequestCreate request,
                                                               @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(borrowService.createRequest(request.itemId(), currentUser));
    }

    @GetMapping("/my-requests")
    public ResponseEntity<List<BorrowRequestResponse>> getMyRequests(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(borrowService.getMyRequests(currentUser));
    }

    @GetMapping("/incoming")
    public ResponseEntity<List<BorrowRequestResponse>> getIncomingRequests(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(borrowService.getIncomingRequests(currentUser));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<BorrowRequestResponse> updateStatus(@PathVariable UUID id,
                                                              @RequestParam RequestStatus status,
                                                              @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(borrowService.changeRequestStatus(id, status, currentUser));
    }
}
