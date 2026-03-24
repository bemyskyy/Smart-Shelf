import { Component, OnInit, inject, PLATFORM_ID, ChangeDetectorRef } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItemService } from '../../core/services/item.service';
import { ItemResponse } from '../../core/models/item.model';

@Component({
  selector: 'app-my-items',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './my-items.component.html'
})
export class MyItemsComponent implements OnInit {
  private itemService = inject(ItemService);
  private fb = inject(FormBuilder);
  private platformId = inject(PLATFORM_ID);
  private cdr = inject(ChangeDetectorRef);

  items: ItemResponse[] = [];
  showAddModal = false;

  itemForm: FormGroup = this.fb.group({
    title: ['', [Validators.required, Validators.minLength(2)]],
    description: ['']
  });

  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.loadMyItems();
    }
  }

  loadMyItems() {
    this.itemService.getMyItems().subscribe({
      next: (data) => {
        this.items = data;
        this.cdr.detectChanges();
      }
    });
  }

  openModal() {
    this.showAddModal = true;
  }

  closeModal() {
    this.showAddModal = false;
    this.itemForm.reset();
  }

  onSubmit() {
    if (this.itemForm.invalid) return;

    this.itemService.createItem(this.itemForm.value).subscribe({
      next: (newItem) => {
        this.items.unshift(newItem);
        this.closeModal();
        this.cdr.detectChanges();
      }
    });
  }
}
