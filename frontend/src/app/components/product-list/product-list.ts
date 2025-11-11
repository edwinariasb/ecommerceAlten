import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { Product } from '../../models/product';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  standalone: true,
  selector: 'app-product-list',
  imports: [CommonModule, FormsModule],
  template: `
  <h1>Produits</h1>

  <div class="toolbar">
    <input type="text" placeholder="Filtrer par nom..."
           [(ngModel)]="filter" (input)="applyFilter()" />
  </div>

  <div class="product-grid">
    <article *ngFor="let product of pagedProducts" class="product-card">
      <img *ngIf="product.image" [src]="product.image" alt="{{product.name}}">
      <h2>{{ product.name }}</h2>
      <p class="category">{{ product.category }}</p>
      <p class="desc">{{ product.description }}</p>
      <p class="price">{{ product.price | currency:'EUR' }}</p>
      <p class="status" [ngClass]="product.inventoryStatus">{{ product.inventoryStatus }}</p>

      <div class="qty-row">
        <button (click)="decrement(product)">-</button>
        <input type="number" [(ngModel)]="tempQty[product.id]" min="1">
        <button (click)="increment(product)">+</button>
      </div>

      <button (click)="addToCart(product)">Ajouter au panier</button>
    </article>
  </div>

  <div class="pagination" *ngIf="filtered.length > pageSize">
    <button (click)="prevPage()" [disabled]="page === 1">Préc.</button>
    <span>Page {{page}} / {{totalPages}}</span>
    <button (click)="nextPage()" [disabled]="page === totalPages">Suiv.</button>
  </div>

  <div *ngIf="errorMessage" class="alert alert-danger">
  {{ errorMessage }}
</div>

<div *ngIf="!errorMessage">
  <!-- ton affichage normal -->
</div>
  `,
  styles: [`
    .product-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
      gap: 1rem;
    }
    .product-card {
      border: 1px solid #ddd;
      padding: 1rem;
      border-radius: .5rem;
      background: #fff;
      display:flex;
      flex-direction:column;
      gap:.5rem;
    }
    .qty-row {
      display:flex;
      gap:.25rem;
      align-items:center;
      margin:.5rem 0;
    }
    .qty-row input {
      width: 60px;
      text-align: center;
    }
    .toolbar { margin-bottom: 1rem; }
    .pagination { margin-top: 1rem; display:flex; gap:.5rem; align-items:center; }
  `]
})
export class ProductList implements OnInit {
  private productService = inject(ProductService);
  private cartService = inject(CartService);
  private authService = inject(AuthService);

  products: Product[] = [];
  filtered: Product[] = [];
  pagedProducts: Product[] = [];

  filter = '';
  page = 1;
  pageSize = 6;
  totalPages = 1;
  errorMessage = '';

  tempQty: Record<number, number> = {};

  ngOnInit(): void {
    this.productService.findAll().subscribe({
    next: (data) => {
      this.products = data;
      this.filtered = data;
      data.forEach(p => this.tempQty[p.id] = 1);
      this.refreshPage();
    },
    error: (err) => {
      console.error('Erreur lors du chargement des produits :', err);

      if (err.status === 0) {
        this.errorMessage = 'Impossible de contacter le serveur. Vérifiez votre connexion.';
      } else if (err.status === 404) {
        this.errorMessage = 'Aucun produit trouvé.';
      } else if (err.status >= 500) {
        this.errorMessage = 'Erreur interne du serveur. Réessayez plus tard.';
      } else {
        this.errorMessage = 'Une erreur inattendue est survenue.';
      }
    },
    complete: () => {
      console.log('Chargement des produits terminé.');
    }
  });
  }

  applyFilter() {
    const f = this.filter.toLowerCase();
    this.filtered = this.products.filter(p =>
      p.name.toLowerCase().includes(f) || p.category?.toLowerCase().includes(f)
    );
    this.page = 1;
    this.refreshPage();
  }

  refreshPage() {
    this.totalPages = Math.max(1, Math.ceil(this.filtered.length / this.pageSize));
    const start = (this.page - 1) * this.pageSize;
    this.pagedProducts = this.filtered.slice(start, start + this.pageSize);
  }

  nextPage() {
    if (this.page < this.totalPages) {
      this.page++;
      this.refreshPage();
    }
  }

  prevPage() {
    if (this.page > 1) {
      this.page--;
      this.refreshPage();
    }
  }

  addToCart(product: Product) {
    if (!this.authService.isLoggedIn()) {
      alert('Veuillez vous connecter pour ajouter au panier');
      return;
    }
    const qty = this.tempQty[product.id] ?? 1;
    this.cartService.add(product, qty);
  }

  increment(product: Product) {
    this.tempQty[product.id] = (this.tempQty[product.id] ?? 1) + 1;
  }

  decrement(product: Product) {
    const current = this.tempQty[product.id] ?? 1;
    this.tempQty[product.id] = Math.max(1, current - 1);
  }
}