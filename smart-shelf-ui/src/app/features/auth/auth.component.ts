import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent {
  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  isLoginMode = true;
  isLoading = false;
  errorMessage = '';

  authForm: FormGroup = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(4)]]
  });

  toggleMode() {
    this.isLoginMode = !this.isLoginMode;
    this.errorMessage = '';
    this.authForm.reset();
  }

  onSubmit() {
    if (this.authForm.invalid) return;

    this.isLoading = true;
    this.errorMessage = '';

    const credentials = this.authForm.value;
    const auth$ = this.isLoginMode
      ? this.authService.login(credentials)
      : this.authService.register(credentials);

    auth$.subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/catalog']);
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Произошла ошибка. Проверьте данные.';
      }
    });
  }

  get f() { return this.authForm.controls; }
}
