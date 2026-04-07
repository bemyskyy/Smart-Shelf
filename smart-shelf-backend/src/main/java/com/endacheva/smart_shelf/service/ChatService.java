package com.endacheva.smart_shelf.service;

import com.endacheva.smart_shelf.dto.record.ChatMessageResponse;
import com.endacheva.smart_shelf.model.BorrowRequest;
import com.endacheva.smart_shelf.model.ChatMessage;
import com.endacheva.smart_shelf.model.User;
import com.endacheva.smart_shelf.repository.BorrowRequestRepository;
import com.endacheva.smart_shelf.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final BorrowRequestRepository borrowRequestRepository;

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(UUID requestId, User currentUser) {
        BorrowRequest request = getAndValidateAccess(requestId, currentUser);

        return chatMessageRepository.findByRequestIdOrderByCreatedAtAsc(requestId).stream()
                .map(msg -> new ChatMessageResponse(
                        msg.getId(),
                        msg.getSender().getUsername(),
                        msg.getContent(),
                        msg.getCreatedAt(),
                        msg.getSender().getId().equals(currentUser.getId())
                )).toList();
    }

    @Transactional
    public ChatMessageResponse sendMessage(UUID requestId, String content, User currentUser) {
        BorrowRequest request = getAndValidateAccess(requestId, currentUser);

        ChatMessage message = ChatMessage.builder()
                .request(request)
                .sender(currentUser)
                .content(content)
                .build();

        ChatMessage saved = chatMessageRepository.save(message);

        return new ChatMessageResponse(
                saved.getId(),
                saved.getSender().getUsername(),
                saved.getContent(),
                saved.getCreatedAt(),
                true
        );
    }

    private BorrowRequest getAndValidateAccess(UUID requestId, User currentUser) {
        BorrowRequest request = borrowRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        boolean isRequester = request.getRequester().getId().equals(currentUser.getId());
        boolean isOwner = request.getItem().getOwner().getId().equals(currentUser.getId());

        if (!isRequester && !isOwner) {
            throw new RuntimeException("У вас нет доступа к этому чату");
        }
        return request;
    }
}
