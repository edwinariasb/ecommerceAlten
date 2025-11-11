import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CartWhislistService {
  private http = inject(HttpClient);
  private base = `${environment.apiUrl}/api/cartWishlist`;

  addToCart(productId: number) {
    return this.http.post<void>(`${this.base}/cart/${productId}`, {});
  }

  removeFromCart(productId: number) {
    return this.http.delete<void>(`${this.base}/cart/${productId}`);
  }

  addToWishlist(productId: number) {
    return this.http.post<void>(`${this.base}/wishlist/${productId}`, {});
  }

  removeFromWishlist(productId: number) {
    return this.http.delete<void>(`${this.base}/wishlist/${productId}`);
  }
}

