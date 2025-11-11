import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CartService } from '../../services/cart.service';

@Component({
  standalone: true,
  selector: 'app-cart',
  imports: [CommonModule],
  template: `
    <h1>Panier</h1>
    <table *ngIf="items.length > 0; else empty">
      <thead>
        <tr>
          <th>Produit</th>
          <th>Prix</th>
          <th>Quantité</th>
          <th>Total</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let item of items">
          <td>{{ item.product.name }}</td>
          <td>{{ item.product.price | currency:'EUR' }}</td>
          <td>
            <input type="number" min="1" [value]="item.qty"
              (change)="update(item.product.id, $any($event.target).value)" />
          </td>
          <td>{{ item.product.price * item.qty | number:'1.2-2' }} €</td>
          <td><button (click)="remove(item.product.id)">Supprimer</button></td>
        </tr>
      </tbody>
    </table>
    <ng-template #empty>
      <p>Votre panier est vide.</p>
    </ng-template>

    <p *ngIf="items.length > 0">
      Total général : {{ getTotal() | currency:'EUR' }}
    </p>
  `
})
export class Cart {
  private cartService = inject(CartService);
  items = this.cartService.items;

  constructor() {
    this.cartService.items$.subscribe(items => this.items = items);
  }

  remove(productId: number) {
    this.cartService.remove(productId);
  }

  update(productId: number, value: string) {
    const qty = Number(value);
    this.cartService.updateQty(productId, qty);
  }

  getTotal(): number {
    return this.items.reduce((acc, item) => acc + item.product.price * item.qty, 0);
  }
}