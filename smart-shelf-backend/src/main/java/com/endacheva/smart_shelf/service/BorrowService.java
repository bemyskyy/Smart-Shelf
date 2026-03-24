package com.endacheva.smart_shelf.service;

import com.endacheva.smart_shelf.dto.record.BorrowRequestResponse;
import com.endacheva.smart_shelf.model.*;
import com.endacheva.smart_shelf.model.enums.ItemStatus;
import com.endacheva.smart_shelf.model.enums.RequestStatus;
import com.endacheva.smart_shelf.repository.BorrowRequestRepository;
import com.endacheva.smart_shelf.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRequestRepository requestRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public BorrowRequestResponse createRequest(UUID itemId, User requester) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Вещь не найдена"));

        if (item.getStatus() != ItemStatus.AVAILABLE) {
            throw new RuntimeException("Вещь уже занята");
        }
        if (item.getOwner().getId().equals(requester.getId())) {
            throw new RuntimeException("Нельзя взять вещь у самого себя");
        }

        BorrowRequest request = BorrowRequest.builder()
                .item(item)
                .requester(requester)
                .status(RequestStatus.PENDING)
                .build();

        return mapToResponse(requestRepository.save(request));
    }

    @Transactional
    public BorrowRequestResponse changeRequestStatus(UUID requestId, RequestStatus newStatus, User currentUser) {
        BorrowRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        Item item = request.getItem();

        if (!item.getOwner().getId().equals(currentUser.getId())) {
            throw new RuntimeException("У вас нет прав на изменение этой заявки");
        }

        if (newStatus == RequestStatus.APPROVED && request.getStatus() == RequestStatus.PENDING) {
            item.setStatus(ItemStatus.IN_USE);
        } else if (newStatus == RequestStatus.RETURNED && request.getStatus() == RequestStatus.APPROVED) {
            item.setStatus(ItemStatus.AVAILABLE);
        } else if (newStatus == RequestStatus.REJECTED && request.getStatus() == RequestStatus.PENDING) {
            // Available
        } else {
            throw new RuntimeException("Недопустимый переход статуса");
        }

        request.setStatus(newStatus);
        itemRepository.save(item);

        return mapToResponse(requestRepository.save(request));
    }

    @Transactional(readOnly = true)
    public List<BorrowRequestResponse> getMyRequests(User currentUser) {
        return requestRepository.findAllByRequesterId(currentUser.getId())
                .stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<BorrowRequestResponse> getIncomingRequests(User currentUser) {
        return requestRepository.findAllByItemOwnerId(currentUser.getId())
                .stream().map(this::mapToResponse).toList();
    }

    private BorrowRequestResponse mapToResponse(BorrowRequest request) {
        return new BorrowRequestResponse(
                request.getId(),
                request.getItem().getTitle(),
                request.getRequester().getUsername(),
                request.getStatus().name()
        );
    }
}
