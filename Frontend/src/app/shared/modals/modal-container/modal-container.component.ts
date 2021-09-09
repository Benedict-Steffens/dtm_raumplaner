import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';
import {DisplayRoomModalComponent} from '../base-room-modal/display-room-modal.component';
import {RoomService} from '../../services/room.service';
import {Room} from '../../models/room';
import {BookingService} from '../../services/booking.service';

@Component({
  selector: 'app-modal-container',
  template: ''
})
export class ModalContainerComponent implements OnInit, OnDestroy {

  public destroy = new Subject<any>();

  public currentDialog = null;

  constructor(private modalService: NgbModal,
              private roomService: RoomService,
              private bookingService: BookingService,
              public activatedRoute: ActivatedRoute,
              public router: Router) { }

  public ngOnInit(): void {
    this.processParams();
  }

  public processParams(): void {
    this.activatedRoute.params.pipe(takeUntil(this.destroy)).subscribe(params => {
      if (params.type === 'rooms') {
        this.displayRoomModal(params.id);
      }

      if (params.type === 'bookings') {
        this.displayBookingModal(params.id);
      }
    });
  }

  public displayRoomModal(roomId: number): void {
    this.roomService.findRoomById(roomId).subscribe(
      (room: Room) => {
        this.currentDialog = this.modalService.open(DisplayRoomModalComponent);
        this.currentDialog.componentInstance.room = room;

        this.currentDialog.result.then(result => {
          this.router.navigateByUrl('/');
        }, reason => {
          this.router.navigateByUrl('/');
        });
      },
      error => {
        this.router.navigateByUrl('/');
      });
  }

  public displayBookingModal(bookingId: number): void {
    // todo
  }

  public ngOnDestroy(): void {
    this.destroy.next();
  }

}
