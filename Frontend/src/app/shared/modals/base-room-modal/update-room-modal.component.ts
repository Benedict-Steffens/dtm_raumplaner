import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder} from '@angular/forms';
import {Room} from '../../models/room';
import {RoomService} from '../../services/room.service';
import {BaseRoomModalComponent} from './base-room-modal.component';
import {ToastService} from '../../services/toast.service';

@Component({
  selector: 'app-update-room-modal',
  templateUrl: './base-room-modal.component.html',
  styleUrls: ['./base-room-modal.component.scss']
})
export class UpdateRoomModalComponent extends BaseRoomModalComponent implements OnInit {

  @Input()
  public room = new Room();

  @Output()
  public updateRoomEvent = new EventEmitter<Room>();

  public title = 'Update Room';
  public btnInscription = 'Update';

  public isNotInformationModal = true;

  constructor(public activeModal: NgbActiveModal,
              public fb: FormBuilder,
              private roomService: RoomService,
              private toastService: ToastService) {
    super(activeModal, fb);
  }

  public submitRoom(): void {
    const roomToUpdate = this.getRoom();
    roomToUpdate.id = this.room.id;

    this.roomService.updateRoom(roomToUpdate).subscribe(
      (updatedRoom: Room) => {
        this.updateRoomEvent.emit(updatedRoom);
        this.activeModal.close();
      },
      error => {
        if (error.error.message.includes('could not execute statement; SQL [n/a]; constraint')) {
          this.isNameAlreadyUsed = true;
          this.roomForm.get('name').setErrors({nameAlreadyUsed: true});
          return;
        }

        this.toastService.showErrorToast(`Could not update room ${this.room.name}.`);
      }
    );
  }
}
