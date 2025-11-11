import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <h1>Connexion</h1>
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <label>Email</label>
      <input type="email" formControlName="email">
      <label>Mot de passe</label>
      <input type="password" formControlName="password">
      <button type="submit" [disabled]="form.invalid">Se connecter</button>
    </form>

    <p *ngIf="error" style="color:red">{{ error }}</p>
  `
})
export class Login {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  error = '';

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  onSubmit() {
    if (this.form.invalid) return;
    this.auth.login(this.form.value as any).subscribe({
      next: () => this.router.navigateByUrl('/'),
      error: () => this.error = 'Identifiants invalides'
    });
  }
}