import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from '../models/product';
import { CartItem } from '../models/cart';

@Injectable({ providedIn: 'root' })
export class CartService {
  private itemsSubject = new BehaviorSubject<CartItem[]>([]);
  items$ = this.itemsSubject.asObservable();

  get items(): CartItem[] {
    return this.itemsSubject.value;
  }

  add(product: Product, qty: number = 1) {
    const items = [...this.items];
    const idx = items.findIndex(i => i.product.id === product.id);
    if (idx > -1) {
      items[idx] = { ...items[idx], qty: items[idx].qty + qty };
    } else {
      items.push({ product, qty });
    }
    this.itemsSubject.next(items);
  }

  remove(productId: number) {
    const items = this.items.filter(i => i.product.id !== productId);
    this.itemsSubject.next(items);
  }

  updateQty(productId: number, qty: number) {
    if (qty <= 0) {
      this.remove(productId);
      return;
    }
    const items = this.items.map(i =>
      i.product.id === productId ? { ...i, qty } : i
    );
    this.itemsSubject.next(items);
  }

  clear() {
    this.itemsSubject.next([]);
  }

  getTotalCount(): number {
    return this.items.reduce((acc, item) => acc + item.qty, 0);
  }
}