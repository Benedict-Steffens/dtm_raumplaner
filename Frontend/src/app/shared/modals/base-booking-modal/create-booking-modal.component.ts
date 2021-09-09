import {BaseBookingModalComponent} from './base-booking-modal.component';
import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {RoomService} from '../../services/room.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder} from '@angular/forms';
import {DataConversionService} from '../../services/data-conversion.service';
import {BookingService} from '../../services/booking.service';
import {Booking} from '../../models/booking';
import {UserService} from '../../services/user.service';
import {ToastService} from '../../services/toast.service';

@Component({
  selector: 'app-create-booking-modal',
  templateUrl: './base-booking-modal.component.html',
  styleUrls: ['./base-booking-modal.component.scss']
})

export class CreateBookingModalComponent extends BaseBookingModalComponent implements OnInit {

  @Output()
  public createBookingEvent = new EventEmitter<Booking>();

  public title = 'Create Booking';

  constructor(private bookingService: BookingService,
              toastService: ToastService,
              activeModal: NgbActiveModal,
              fb: FormBuilder,
              roomService: RoomService,
              dataConversionService: DataConversionService,
              userService: UserService) {
    super(activeModal, fb, dataConversionService, userService, roomService, toastService);
  }

  public submitBooking(): void {
    if (this.getRoomRadioBtnValue() === undefined) {
      this.isRoomSelected = false;
      return;
    }

    this.getBooking().subscribe(
      (booking: Booking) => {
        this.bookingService.createBooking(booking).subscribe(
          (createdBooking: Booking) => {
            this.createBookingEvent.emit(createdBooking);
            this.activeModal.close();
          },
          error => {
            this.toastService.showErrorToast('Could not create booking.');
          }
        );
      }
    );
  }
}
