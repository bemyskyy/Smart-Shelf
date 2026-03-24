import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BorrowService } from '../../core/services/borrow.service';
import { BorrowRequestResponse } from '../../core/models/borrow.model';
import { ToastService } from '../../core/services/toast.service';

@Component({
  selector: 'app-requests',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './requests.component.html'
})
export class RequestsComponent implements OnInit {
  private borrowService = inject(BorrowService);
  private cdr = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);

  showRejectConfirm = false;
  requestToRejectId: string | null = null;

  activeTab: 'incoming' | 'outgoing' = 'incoming';

  incomingRequests: BorrowRequestResponse[] = [];
  outgoingRequests: BorrowRequestResponse[] = [];

  ngOnInit(): void {
    this.loadRequests();
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
}
