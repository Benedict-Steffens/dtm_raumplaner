import {Injectable} from '@angular/core';
import {ToastService} from '../services/toast.service';

export const TOKEN_NAME = 'login-token';
export const USER_NAME = 'userName';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token: string;

  constructor(private toastService: ToastService) { }

  public setToken(token: string): void {
    this.token = token;
    localStorage.setItem(TOKEN_NAME, this.token);
  }

  public getToken(): string {
    return localStorage[TOKEN_NAME];
  }

  public isLoggedIn(): boolean {
    return this.getToken() != null;
  }

  public getKey(): string {
    return TOKEN_NAME;
  }

  public logout(): void {
    if (this.isLoggedIn()) {
      this.token = null;
      localStorage.removeItem(TOKEN_NAME);
      this.toastService.showSuccessToast('You have been logged out successfully.');
    }
  }
}
