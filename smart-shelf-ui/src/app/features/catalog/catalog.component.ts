import { Component, OnInit, inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms'; // <-- 1. Добавили модуль для работы с формами (ngModel)
import { ItemService } from '../../core/services/item.service';
import { BorrowService } from '../../core/services/borrow.service';
import { ItemResponse } from '../../core/models/item.model';
import { ToastService } from '../../core/services/toast.service';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule], // <-- 2. Подключили модуль сюда
  templateUrl: './catalog.component.html'
})
export class CatalogComponent implements OnInit {
  private itemService = inject(ItemService);
  private borrowService = inject(BorrowService);
  private platformId = inject(PLATFORM_ID);
  private cdr = inject(ChangeDetectorRef);
  private toastService = inject(ToastService);

  items: ItemResponse[] = [];
  searchQuery = ''; // <-- Переменная для хранения текста поиска
  requestedItemIds = new Set<string>();

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.loadItems();
    }
  }

  loadItems() {
    this.itemService.getAvailableItems().subscribe({
      next: (data) => {
        this.items = data;
        this.cdr.detectChanges();
      },
      error: () => this.toastService.showError('Не удалось загрузить каталог')
    });
  }

  requestItem(itemId: string) {
    this.borrowService.createRequest({ itemId }).subscribe({
      next: () => {
        this.toastService.showSuccess('Заявка успешно отправлена владельцу!');
        this.requestedItemIds.add(itemId);
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.toastService.showError(err.error?.message || 'Ошибка при отправке заявки');
      }
    });
  }

  // <-- 3. Магия фильтрации! Этот метод автоматически пересчитывает массив при вводе текста
  get filteredItems(): ItemResponse[] {
    if (!this.searchQuery.trim()) {
      return this.items; // Если поиск пуст - отдаем всё
    }

    const query = this.searchQuery.toLowerCase().trim();

    return this.items.filter(item => {
      const matchTitle = item.title.toLowerCase().includes(query);
      // Проверяем описание (с защитой от null, если описания нет)
      const matchDesc = item.description ? item.description.toLowerCase().includes(query) : false;

      return matchTitle || matchDesc;
    });
  }
}
