import {Component, OnInit} from '@angular/core';
import {RoomService} from 'src/app/shared/services/room.service';
import {Room} from 'src/app/shared/models/room';
import {ToastService} from '../../shared/services/toast.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {CreateRoomModalComponent} from '../../shared/modals/base-room-modal/create-room-modal.component';
import {
  faCheckCircle,
  faPlusCircle,
  faSortAlphaDown,
  faSortAlphaUp,
  faSortNumericDown,
  faSortNumericUp
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent implements OnInit {

  public allRooms: Array<Room> = [];
  public filteredRooms: Array<Room> = [];

  public orderByName: string;
  public orderByCapacity: string;
  public isOnlyAvailableRooms: boolean;

  public sortAlphaDownIcon = faSortAlphaDown;
  public sortAlphaUpIcon = faSortAlphaUp;
  public sortNumericDownIcon = faSortNumericDown;
  public plusCircleIcon = faPlusCircle;
  public checkCircleIcon = faCheckCircle;
  public sortNumericUpIcon = faSortNumericUp;

  constructor(
    private roomService: RoomService,
    private toastService: ToastService,
    private modalService: NgbModal) { }

  public ngOnInit(): void {
    this.getAllRooms();
  }

  public getAllRooms(): void {
    this.roomService.findAllRooms(this.orderByName).subscribe(
      (rooms: Array<Room>) => {
        this.allRooms = rooms;
        this.filterRooms();
      },
      error => {
        this.showErrorToast('Could not load rooms.');
      }
    );
  }

  public changeRoomOrderBySize(): void {
    if (this.orderByCapacity === 'asc') {
      this.orderByCapacity = 'desc';
      this.filteredRooms.sort((a: Room, b: Room) => (a.capacity < b.capacity) ? 1 : -1);
      return;
    }
    this.orderByCapacity = 'asc';
    this.orderByName = '';
    this.filteredRooms.sort((a: Room, b: Room) => (a.capacity >= b.capacity) ? 1 : -1);
  }

  public changeRoomOrderAlphabetical(): void {
    if (this.orderByName === 'asc') {
      this.orderByName = 'desc';
      this.filteredRooms.sort((a: Room, b: Room) => (a.name.toUpperCase() < b.name.toUpperCase()) ? 1 : -1);
      return;
    }
    this.orderByName = 'asc';
    this.orderByCapacity = '';
    this.filteredRooms.sort((a: Room, b: Room) => (a.name.toUpperCase() >= b.name.toUpperCase()) ? 1 : -1);
  }

  public filterRooms(name?: KeyboardEvent): void {
    if (name) {
      this.filteredRooms = this.allRooms.filter(
        (room: Room) => room.name.includes((name.target as HTMLInputElement).value)
      );
      return;
    }

    this.filteredRooms = this.allRooms;
    this.orderByName = '';
    this.changeRoomOrderAlphabetical();
    this.isOnlyAvailableRooms = false;
  }

  public showOnlyAvailableRooms(): void {
    this.isOnlyAvailableRooms = !this.isOnlyAvailableRooms;
  }

  public showErrorToast(message: string): void {
    this.toastService.showErrorToast(message);
  }

  public showSuccessToast(message: string): void {
    this.toastService.showSuccessToast(message);
  }

  public openCreateRoomModal(): void {
    const createRoomModalRef = this.modalService.open(CreateRoomModalComponent);
    createRoomModalRef.componentInstance.createRoomEvent.subscribe((createdRoom: Room) => {
      this.showSuccessToast(`The room ${createdRoom.name} was created!`);
      this.updateRoomList();
    });
  }

  public updateRoomList(): void {
    this.getAllRooms();
    this.filterRooms();
  }

  public removeRoomFromList(room: Room): void {
    const indexOfRoomToBeRemoved = this.filteredRooms.indexOf(room, 0);

    if (indexOfRoomToBeRemoved > -1) {
      this.filteredRooms.splice(indexOfRoomToBeRemoved, 1);
    }
  }

  public addRoomToList(): void {
    this.updateRoomList();
  }

  public deleteRoom(roomId: number): void {
    this.roomService.deleteRoom(roomId).subscribe(
      (id: number) => {
        if (roomId === id) {
          this.showSuccessToast(`Room was successfully deleted.`);
          this.updateRoomList();
        }
      },
      error => {
        if (error.error.httpStatus === 'UNPROCESSABLE_ENTITY') {
          this.showErrorToast(error.error.message);
          this.updateRoomList();
        }
      }
    );
  }
}
