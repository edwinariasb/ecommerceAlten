import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { RegisterRequest } from '../models/RegisterRequest';
import { LoginRequest } from '../models/LoginRequest';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private http = inject(HttpClient);
  private api = environment.apiUrl;
  private readonly TOKEN_KEY = 'auth_token';

  register(payload: RegisterRequest) {
    return this.http.post(`${this.api}/account`, payload);
  }

  login(payload: LoginRequest) {
    return this.http.post<{ token: string }>(`${this.api}/token`, payload).pipe(
      tap(res => this.setToken(res.token))
    );
  }

  setToken(token: string) {
    if (typeof window === 'undefined' || !window.localStorage) return;
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    if (typeof window === 'undefined' || !window.localStorage) {
    return null;
  }
    return localStorage.getItem(this.TOKEN_KEY);
  }

  logout() {
    if (typeof window === 'undefined' || !window.localStorage) return;
    localStorage.removeItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getCurrentEmail(): string | null {
    const token = this.getToken();
    if (!token) return null;
    try {
      // JWT = header.payload.signature
      const payload = token.split('.')[1];
      const json = atob(payload);
      const data = JSON.parse(json);
      // on avait mis l'email dans le subject (sub) côté Spring
      return data.sub || null;
    } catch (e) {
      return null;
    }
  }
}