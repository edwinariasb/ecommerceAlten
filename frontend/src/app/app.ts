import { Component, inject } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CartService } from './services/cart.service'; // tu peux le garder si tu veux toujours montrer le badge local
import { AuthService } from './services/auth.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive],
  template: `
  <div class="layout">
    <aside class="sidebar">
      <h2>Alten Shop</h2>
      <nav>
        <a routerLink="" routerLinkActive="active" [routerLinkActiveOptions]="{exact:true}">Produits</a>
        <a routerLink="/cart" routerLinkActive="active">
          Panier
          <span class="badge" *ngIf="cartCount > 0">{{ cartCount }}</span>
        </a>
        <a routerLink="/contact" routerLinkActive="active">Contact</a>
      </nav>

      <div class="auth-block">
        <ng-container *ngIf="loggedIn; else notLogged">
          <p class="user">Bonjour {{ email }}</p>
          <button (click)="logout()">Se déconnecter</button>
        </ng-container>
        <ng-template #notLogged>
          <button (click)="goLogin()">Se connecter</button>
        </ng-template>
      </div>
    </aside>
    <main class="content">
      <router-outlet></router-outlet>
    </main>
  </div>
  `,
  styles: [`
    .layout { display: flex; min-height: 100vh; }
    .sidebar { width: 220px; background: #222; color: #fff; padding: 1rem; display:flex; flex-direction:column; gap:1rem; }
    nav a { display:block; color:#fff; text-decoration:none; padding:.5rem 0; }
    .content { flex:1; padding:1rem 2rem; }
    .badge { background: crimson; border-radius: 999px; padding: 0 .5rem; font-size: .75rem; margin-left: .5rem; }
    .auth-block button { background:#fff; border:none; padding:.25rem .5rem; cursor:pointer; }
    .user { font-size:.85rem; margin-bottom:.5rem; }
  `]
})
export class App {
  private cartService = inject(CartService);
  private auth = inject(AuthService);
  private router = inject(Router);

  cartCount = 0;
  loggedIn = false;
  email: string | null = null;

  constructor() {
    // si tu gardes le panier côté front
    this.cartService.items$.subscribe(() => {
      this.cartCount = this.cartService.getTotalCount();
    });

    this.refreshAuth();
  }

  refreshAuth() {
    this.loggedIn = this.auth.isLoggedIn();
    this.email = this.auth.getCurrentEmail();
  }

  goLogin() {
    this.router.navigate(['/login']);
  }

  logout() {
    this.auth.logout();
    this.refreshAuth();
    this.router.navigate(['/login']);
  }
}