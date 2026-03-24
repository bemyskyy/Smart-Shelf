import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { BorrowService } from '../../../core/services/borrow.service';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './layout.component.html'
})
export class LayoutComponent implements OnInit {
  private authService = inject(AuthService);
  private borrowService = inject(BorrowService);
  private router = inject(Router);

  pendingCount$ = this.borrowService.pendingCount$;

  ngOnInit() {
    this.borrowService.updatePendingCount();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
