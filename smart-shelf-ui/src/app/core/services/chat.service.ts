import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { ChatMessageResponse } from '../models/chat.model';

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/requests`;

  getMessages(requestId: string): Observable<ChatMessageResponse[]> {
    return this.http.get<ChatMessageResponse[]>(`${this.apiUrl}/${requestId}/messages`);
  }

  sendMessage(requestId: string, content: string): Observable<ChatMessageResponse> {
    return this.http.post<ChatMessageResponse>(`${this.apiUrl}/${requestId}/messages`, { content });
  }
}
