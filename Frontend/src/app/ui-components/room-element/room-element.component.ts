import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Room} from 'src/app/shared/models/room';
import {BookingService} from '../../shared/services/booking.service';
import {faCheckCircle, faClock, faEdit, faEllipsisV, faTrashAlt} from '@fortawesome/free-solid-svg-icons';
import {ToastService} from '../../shared/services/toast.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {UpdateRoomModalComponent} from '../../shared/modals/base-room-modal/update-room-modal.component';
import {DisplayRoomModalComponent} from '../../shared/modals/base-room-modal/display-room-modal.component';
import {RoomService} from '../../shared/services/room.service';

@Component({
  selector: 'app-room-element',
  templateUrl: './room-element.component.html',
  styleUrls: ['./room-element.component.scss']
})
export class RoomElementComponent implements OnInit, OnDestroy {

  @Input()
  public room: Room;
  @Input()
  public isOnlyAvailableRooms: boolean;

  @Output()
  public updateList = new EventEmitter();
  @Output()
  public removeRoomFromList = new EventEmitter<Room>();

  public availableInMinutes;

  public editIcon = faEdit;
  public clockIcon = faClock;
  public ellipsisVIcon = faEllipsisV;
  public checkCircleIcon = faCheckCircle;
  public trashAltIcon = faTrashAlt;

  private timerRoomAvailabilityCheck;

  constructor(
    private bookingService: BookingService,
    private roomService: RoomService,
    private toastService: ToastService,
    private modalService: NgbModal) { }

  public ngOnInit(): void {
    this.getRoomAvailability();
  }

  public ngOnDestroy(): void {
    clearInterval(this.timerRoomAvailabilityCheck);
  }

  public getRoomAvailability(): void {
    this.bookingService.getOccupationDurationByRoomId(this.room.id).subscribe(
      (availableMinutes: number) => {
        this.availableInMinutes = availableMinutes;
        this.startAvailabilityCheckTimer();
      },
      error => this.showErrorToast(`Availability of room ${this.room.name} could not be retrieved.`)
    );
  }

  public startAvailabilityCheckTimer(): void {
    this.timerRoomAvailabilityCheck = setInterval(() => {
      if (this.availableInMinutes > 0) {
        this.availableInMinutes--;
        return;
      }
      clearInterval(this.timerRoomAvailabilityCheck);
      this.getRoomAvailability();
    }, 60000);
  }

  public showErrorToast(message: string): void {
    this.toastService.showErrorToast(message);
  }

  public showSuccessToast(message: string): void {
    this.toastService.showSuccessToast(message);
  }

  public deleteToast(): void {
    this.roomService.checkRoomForDependencies(this.room.id).subscribe(
      (hasDependencies: boolean) => {
        if (hasDependencies) {
          this.showErrorToast('Room can not be deleted as there are still booking(s) related to the room.');
          return;
        }

        this.toastService.showDeleteObjectToast(`Room ${this.room.name} was deleted successfully!`, this.room.id);
        this.removeRoomFromList.emit(this.room);
      },
      error => {
        this.toastService.showDeleteObjectToast(`Room ${this.room.name} was deleted successfully!`, this.room.id);
        this.removeRoomFromList.emit(this.room);
      }
    );
  }

  public openDisplayRoomModal(): void {
    const displayRoomModalRef = this.modalService.open(DisplayRoomModalComponent);
    displayRoomModalRef.componentInstance.room = this.room;
  }

  public openUpdateRoomModal(): void {
    const updateRoomModalRef = this.modalService.open(UpdateRoomModalComponent);
    updateRoomModalRef.componentInstance.room = this.room;
    updateRoomModalRef.componentInstance.updateRoomEvent.subscribe((updatedRoom: Room) => {
      this.room = updatedRoom;
      this.updateList.emit();
      this.showSuccessToast(`Room ${updatedRoom.name} was successfully updated.`);
    });
  }
}
