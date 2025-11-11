import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <h1>Créer un compte</h1>
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <label>Username</label>
      <input formControlName="username" />

      <label>Prénom</label>
      <input formControlName="firstname" />

      <label>Email</label>
      <input type="email" formControlName="email" />

      <label>Mot de passe</label>
      <input type="password" formControlName="password" />

      <button type="submit" [disabled]="form.invalid">Créer le compte</button>
    </form>

    <p *ngIf="ok" class="success">✅ Compte créé avec succès !</p>
    <p *ngIf="error" class="error">⚠️ {{ error }}</p>

    <p *ngIf="ok" style="color:green">Compte créé, vous pouvez vous connecter.</p>
  `
})
export class Register {
  private fb = inject(FormBuilder);
  private auth = inject(AuthService);
  private router = inject(Router);

  ok = false;
  error = '';

  form = this.fb.group({
    username: ['', Validators.required],
    firstname: [''],
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  onSubmit() {
    if (this.form.invalid) return;
    this.auth.register(this.form.value as any).subscribe({
    next: () => {
      this.ok = true;
      this.error = '';
      setTimeout(() => this.router.navigate(['/login']), 1000);
    },
    error: (err) => {
      console.error('Erreur d’inscription :', err);
      if (err.status === 409) {
        this.error = 'Un compte existe déjà avec cet email.';
      } else if (err.status === 400) {
        this.error = 'Les informations saisies sont invalides.';
      } else {
        this.error = 'Une erreur inattendue est survenue. Réessayez plus tard.';
      }
      this.ok = false;
    }
  });
  }
}