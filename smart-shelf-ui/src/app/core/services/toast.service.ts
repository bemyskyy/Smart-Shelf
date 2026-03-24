import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface Toast {
  id: number;
  message: string;
  type: 'success' | 'error';
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private toastsSubject = new BehaviorSubject<Toast[]>([]);
  public toasts$ = this.toastsSubject.asObservable();
  private counter = 0;

  showSuccess(message: string) {
    this.addToast(message, 'success');
  }

  showError(message: string) {
    this.addToast(message, 'error');
  }

  private addToast(message: string, type: 'success' | 'error') {
    const id = this.counter++;
    const currentToasts = this.toastsSubject.value;
    this.toastsSubject.next([...currentToasts, { id, message, type }]);

    setTimeout(() => this.removeToast(id), 3000);
  }

  removeToast(id: number) {
    this.toastsSubject.next(this.toastsSubject.value.filter(t => t.id !== id));
  }
}
