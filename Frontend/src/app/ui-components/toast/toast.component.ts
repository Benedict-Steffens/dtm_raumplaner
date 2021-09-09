import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ToastService} from '../../shared/services/toast.service';
import {Toast} from '../../shared/models/toast';

@Component({
  selector: 'app-toasts',
  templateUrl: './toast.component.html',
  styleUrls: ['./toast.component.scss'],
  host: {'[class.ngb-toasts]': 'true'}
})
export class ToastComponent {

  @Output()
  public addRoomToList = new EventEmitter();
  @Output()
  public deleteRoom = new EventEmitter<number>();

  constructor(public toastService: ToastService) { }

  public abortDeletion(toast: Toast): void {
    this.toastService.remove(toast);
    this.toastService.showSuccessToast('The deletion was successfully aborted.');

    this.addRoomToList.emit();
  }

  public onHide(toast: any): void {
    this.toastService.remove(toast);

    if (toast.isDeleteToast === true) {
      this.deleteRoom.emit(toast.roomId);
    }
  }
}
