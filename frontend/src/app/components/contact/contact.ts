import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-contact',
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <h1>Contact</h1>
    <form [formGroup]="form" (ngSubmit)="onSubmit()">
      <label>Email</label>
      <input type="email" formControlName="email" />
      <div class="error" *ngIf="form.get('email')?.invalid && form.get('email')?.touched">
        Email obligatoire et valide.
      </div>

      <label>Message</label>
      <textarea formControlName="message" rows="5"></textarea>
      <div class="error" *ngIf="form.get('message')?.invalid && form.get('message')?.touched">
        Message obligatoire, max 300 caractères.
      </div>

      <button type="submit" [disabled]="form.invalid">Envoyer</button>
    </form>

    <p *ngIf="sent" class="success">Demande de contact envoyée avec succès</p>
  `,
  styles: [`
    form { display:flex; flex-direction:column; gap:.5rem; max-width:400px; }
    .error { color:crimson; font-size:.8rem; }
    .success { margin-top:1rem; color:green; }
  `]
})
export class Contact implements OnInit {

  form: any;
  sent = false;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]]
    });
  }

  onSubmit() {
    if (this.form.invalid) return;
    // ici tu pourrais appeler ton back
    this.sent = true;
    this.form.reset();
  }
}