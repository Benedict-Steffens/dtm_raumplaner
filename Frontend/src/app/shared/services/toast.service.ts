import {Injectable} from '@angular/core';
import {Toast} from '../models/toast';

@Injectable({
  providedIn: 'root'
})
export class ToastService {

  public toasts: Toast[] = [];

  constructor() {}

  public showErrorToast(message: string): void {
    const toast = this.getToast(null, message, 'Error', 'bg-danger text-white', false);
    this.toasts.push(toast);
  }

  public showSuccessToast(message: string): void {
    const toast = this.getToast(null, message, 'Success', 'bg-success text-white', false);
    this.toasts.push(toast);
  }

  public showDeleteObjectToast(message: string, roomId: number): void {
    const toast = this.getToast(roomId, message, null, 'bg-success text-white', true);
    this.toasts.push(toast);
  }

  public remove(toast: any): void {
    this.toasts = this.toasts.filter((t: Toast) => t !== toast);
  }

  private getToast(roomId: number, message: string, headerText: string, cssClasses: string, isDeleteToast: boolean): Toast {
    return new Toast(roomId, message, headerText, cssClasses, isDeleteToast);
  }
}
