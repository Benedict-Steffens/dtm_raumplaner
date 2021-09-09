import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder} from '@angular/forms';
import {Room} from '../../models/room';
import {RoomService} from '../../services/room.service';
import {BaseRoomModalComponent} from './base-room-modal.component';
import {ToastService} from '../../services/toast.service';

@Component({
  selector: 'app-create-room-modal',
  templateUrl: './base-room-modal.component.html',
  styleUrls: ['./base-room-modal.component.scss']
})
export class CreateRoomModalComponent extends BaseRoomModalComponent implements OnInit {

  @Output()
  public createRoomEvent = new EventEmitter<Room>();

  public title = 'Create Room';
  public btnInscription = 'Create';

  public isNotInformationModal = true;

  constructor(public activeModal: NgbActiveModal,
              public fb: FormBuilder,
              private roomService: RoomService,
              private toastService: ToastService) {
    super(activeModal, fb);
  }

  public submitRoom(): void {
    this.roomService.createRoom(this.getRoom()).subscribe(
      (createdRoom: Room) => {
        this.createRoomEvent.emit(createdRoom);
        this.activeModal.close();
      },
      error => {
        if (error.error.httpStatus === 'UNPROCESSABLE_ENTITY') {
          this.isNameAlreadyUsed = true;
          this.roomForm.get('name').setErrors({nameAlreadyUsed: true});
          return;
        }

        this.toastService.showErrorToast('Could not create room.');
      }
    );
  }
}
