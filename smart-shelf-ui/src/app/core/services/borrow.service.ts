import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { BorrowRequestCreate, BorrowRequestResponse } from '../models/borrow.model';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BorrowService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/requests`;

  private pendingCountSubject = new BehaviorSubject<number>(0);
  public pendingCount$ = this.pendingCountSubject.asObservable();

  updatePendingCount() {
    this.getIncomingRequests().subscribe({
      next: (requests) => {
        const count = requests.filter(r => r.status === 'PENDING').length;
        this.pendingCountSubject.next(count);
      }
    });
  }

  createRequest(request: BorrowRequestCreate): Observable<BorrowRequestResponse> {
    return this.http.post<BorrowRequestResponse>(this.apiUrl, request);
  }

  getMyRequests(): Observable<BorrowRequestResponse[]> {
    return this.http.get<BorrowRequestResponse[]>(`${this.apiUrl}/my-requests`);
  }

  getIncomingRequests(): Observable<BorrowRequestResponse[]> {
    return this.http.get<BorrowRequestResponse[]>(`${this.apiUrl}/incoming`);
  }

  updateStatus(id: string, status: string): Observable<BorrowRequestResponse> {
    return this.http.put<BorrowRequestResponse>(`${this.apiUrl}/${id}/status?status=${status}`, {});
  }
}
