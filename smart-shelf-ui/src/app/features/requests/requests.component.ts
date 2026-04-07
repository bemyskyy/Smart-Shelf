import { Component, OnInit, OnDestroy, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { BorrowService } from '../../core/services/borrow.service';
import { BorrowRequestResponse } from '../../core/models/borrow.model';
import { ToastService } from '../../core/services/toast.service';
import { ChatService } from '../../core/services/chat.service';
import { ChatMessageResponse } from '../../core/models/chat.model';
import { Subscription, interval } from 'rxjs';
import { switchMap, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-requests',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './requests.component.html'
})
export class RequestsComponent implements OnInit, OnDestroy {
  private borrowService = inject(BorrowService);
  private cdr = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);
  private chatService = inject(ChatService);

  showRejectConfirm = false;
  requestToRejectId: string | null = null;

  activeTab: 'incoming' | 'outgoing' = 'incoming';

  incomingRequests: BorrowRequestResponse[] = [];
  outgoingRequests: BorrowRequestResponse[] = [];

  activeChatRequestId: string | null = null;
  chatMessages: ChatMessageResponse[] = [];
  newMessage = '';
  private chatSubscription?: Subscription;

  ngOnInit(): void {
    this.loadRequests();
  }

  ngOnDestroy(): void {
    this.closeChat();
  }

  setTab(tab: 'incoming' | 'outgoing') {
    this.activeTab = tab;
  }

  loadRequests() {
    this.borrowService.getIncomingRequests().subscribe({
      next: (data) => {
        this.incomingRequests = data;
        this.cdr.detectChanges();
      }
    });

    this.borrowService.getMyRequests().subscribe({
      next: (data) => {
        this.outgoingRequests = data;
        this.cdr.detectChanges();
      }
    });
  }

  changeStatus(id: string, newStatus: string) {
    this.borrowService.updateStatus(id, newStatus).subscribe({
      next: () => {
        this.toastService.showSuccess('Статус заявки успешно изменен!');
        this.loadRequests();
        this.borrowService.updatePendingCount();
      },
      error: () => this.toastService.showError('Произошла ошибка')
    });
  }

  confirmReject(id: string) {
    this.requestToRejectId = id;
    this.showRejectConfirm = true;
  }

  cancelReject() {
    this.showRejectConfirm = false;
    this.requestToRejectId = null;
  }

  executeReject() {
    if (this.requestToRejectId) {
      this.changeStatus(this.requestToRejectId, 'REJECTED');
      this.cancelReject();
    }
  }

  openChat(requestId: string) {
    this.activeChatRequestId = requestId;

    this.chatSubscription = interval(3000).pipe(
      startWith(0),
      switchMap(() => this.chatService.getMessages(requestId))
    ).subscribe({
      next: (messages) => {
        this.chatMessages = messages;
        this.cdr.detectChanges();
      },
      error: () => this.toastService.showError('Ошибка при загрузке чата')
    });
  }

  closeChat() {
    this.activeChatRequestId = null;
    this.chatMessages = [];
    if (this.chatSubscription) {
      this.chatSubscription.unsubscribe();
    }
  }

  sendMessage() {
    if (!this.newMessage.trim() || !this.activeChatRequestId) return;

    const content = this.newMessage;
    this.newMessage = '';

    this.chatService.sendMessage(this.activeChatRequestId, content).subscribe({
      next: (msg) => {
        this.chatMessages.push(msg);
        this.cdr.detectChanges();
      },
      error: () => this.toastService.showError('Не удалось отправить сообщение')
    });
  }
}
