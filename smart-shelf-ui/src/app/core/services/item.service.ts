import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ItemRequest, ItemResponse } from '../models/item.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/items`;

  getAvailableItems(): Observable<ItemResponse[]> {
    return this.http.get<ItemResponse[]>(this.apiUrl);
  }

  getMyItems(): Observable<ItemResponse[]> {
    return this.http.get<ItemResponse[]>(`${this.apiUrl}/my`);
  }

  createItem(item: ItemRequest): Observable<ItemResponse> {
    return this.http.post<ItemResponse>(this.apiUrl, item);
  }
}
