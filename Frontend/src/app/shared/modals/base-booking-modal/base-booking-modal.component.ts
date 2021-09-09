import {
  AfterViewInit,
  Component,
  OnInit,
  QueryList,
  ViewChildren
} from '@angular/core';
import {NgbActiveModal, NgbDate} from '@ng-bootstrap/ng-bootstrap';
import {AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {Booking} from '../../models/booking';
import {Room} from '../../models/room';
import {LabelType, Options} from 'ng5-slider';
import {RoomService} from '../../services/room.service';
import {LocalDate, LocalDateTime} from '@js-joda/core';
import {RoomListElementComponent} from './room-list-element/room-list-element.component';
import {DataConversionService} from '../../services/data-conversion.service';
import {faCalendarAlt} from '@fortawesome/free-solid-svg-icons';
import {UserService} from '../../services/user.service';
import {User} from '../../models/user';
import {Observable, Observer} from 'rxjs';
import {ToastService} from '../../services/toast.service';

@Component({
  templateUrl: './base-booking-modal.component.html',
  styleUrls: ['./base-booking-modal.component.scss']
})
export class BaseBookingModalComponent implements OnInit, AfterViewInit {

  @ViewChildren(RoomListElementComponent)
  public listedRooms: QueryList<RoomListElementComponent>;

  public rangeStartBooking: number;
  public rangeEndBooking: number;

  public calenderIcon = faCalendarAlt;

  public title: string;

  public isRoomSelected = true;

  public rooms = new Array<Room>();

  public bookingForm: FormGroup;

  public rangeSliderOptions: Options = {
    floor: 0,
    ceil: 1439,
    getPointerColor: (value: number): string => {return '#D79922';},
    translate: (value: number, label: LabelType): string => {
      return this.onRangeInputChange(value, label);
    }
  };

  private mockedCurrentUserId = 1;

  constructor(
    public activeModal: NgbActiveModal,
    public fb: FormBuilder,
    private dataConversionService: DataConversionService,
    private userService: UserService,
    private roomService: RoomService,
    public toastService: ToastService) { }

  public ngOnInit(): void {
    this.getRooms();
    this.initForm();
  }

  public ngAfterViewInit(): void {
    this.dateTimeChange();
  }

  public getStartAndEndTime(): Array<{ hour: number, minute: number }> {
    const timeNow = LocalDateTime.now();
    const startBooking = {hour: timeNow.hour(), minute: timeNow.minute() + 1};
    let endBooking: { hour: number, minute: number };

    if (startBooking.hour >= 23) {
      endBooking = {hour: 23, minute: 59};
    } else {
      endBooking = {hour: timeNow.hour() + 1, minute: 0};
    }

    return [startBooking, endBooking];
  }

  public initRangeSliderValues(startBooking: { hour: number, minute: number }, endBooking: { hour: number, minute: number }): void {
    this.rangeStartBooking = startBooking.hour * 60 + startBooking.minute;
    this.rangeEndBooking = endBooking.hour * 60 + endBooking.minute;
  }

  public initForm(): void {
    const startAndEndTime = this.getStartAndEndTime();

    this.bookingForm = this.fb.group({
        purpose: ['', [
          Validators.required,
          Validators.maxLength(100)
        ]],
        date: [this.dataConversionService.convertLocalDateToNgbDate(LocalDate.now()), [
          Validators.required,
        ]],
        startBooking: [startAndEndTime[0], [
          Validators.required,
        ]],
        endBooking: [startAndEndTime[1], [
          Validators.required,
        ]],
      },
      {
        validator: invalidBookingTimesValidator()
      });

    this.initRangeSliderValues(startAndEndTime[0], startAndEndTime[1]);
  }

  public checkRoomSelection(): void {
    if (this.getRoomRadioBtnValue() !== undefined) {
      this.isRoomSelected = true;
    }
  }

  public getBooking(): Observable<Booking> {
    const bookingObservable = new Observable<Booking>((observer: Observer<Booking>) => {
      const booking = new Booking();

      booking.purpose = this.bookingForm.controls.purpose.value;

      const date = this.bookingForm.controls.date.value;
      booking.date = LocalDate.of(date.year, date.month, date.day);

      const startTime = this.bookingForm.controls.startBooking.value;
      booking.startBooking = LocalDateTime.of(date.year, date.month, date.day, startTime.hour, startTime.minute);

      const endTime = this.bookingForm.controls.endBooking.value;
      booking.endBooking = LocalDateTime.of(date.year, date.month, date.day, endTime.hour, endTime.minute);

      booking.room = this.getRoomRadioBtnValue();

      this.userService.findUserById(this.mockedCurrentUserId).subscribe(
        (user: User) => {
          booking.appUser = user;
          observer.next(booking);
        },
        (error: Error) => {
          observer.error(error);
        });
    });

    return bookingObservable;
  }

  public getRooms(): void {
    this.roomService.findAllRooms('asc').subscribe(
      (rooms: Array<Room>) => {
        this.rooms = rooms;
      },
      error => {
        this.toastService.showErrorToast('Could not retrieve rooms.');
      }
    );
  }

  public getRoomRadioBtnValue(): Room {
    let roomId: number;
    this.listedRooms.forEach((roomObject: RoomListElementComponent) => {
      if (roomObject.getIfRadioBtnChecked()) {
        roomId = roomObject.room.id;
      }
    });
    return this.rooms.find((room: Room) => room.id === roomId);
  }

  public onRangeInputChange(value: number, label: LabelType): string {
    const hours = Math.floor(value / 60);
    const minutes = value % 60;
    const timeObject = {hour: hours, minute: minutes};

    if (label === 0) {
      this.bookingForm.controls.startBooking.setValue(timeObject);
    }

    if (label === 1) {
      this.bookingForm.controls.endBooking.setValue(timeObject);
    }

    if (minutes < 10) {
      return `${hours}:0${minutes}`;
    }

    return `${hours}:${minutes}`;
  };

  public dateTimeChange(): void {
    const selectedDate = this.bookingForm.controls.date.value;

    const startBooking = LocalDateTime.of(selectedDate.year,
      selectedDate.month,
      selectedDate.day,
      this.bookingForm.controls.startBooking.value.hour,
      this.bookingForm.controls.startBooking.value.minute);

    let endBooking = LocalDateTime.of(selectedDate.year,
      selectedDate.month,
      selectedDate.day,
      this.bookingForm.controls.endBooking.value.hour,
      this.bookingForm.controls.endBooking.value.minute);

    if (startBooking.isAfter(endBooking)) {
      if (startBooking.hour() === 23 && startBooking.minute() === 59) {
        endBooking = startBooking;
      } else {
        endBooking = startBooking.plusMinutes(1);
      }
    }

    this.rangeStartBooking = startBooking.hour() * 60 + startBooking.minute();
    this.rangeEndBooking = endBooking.hour() * 60 + endBooking.minute();

    this.listedRooms.forEach((roomObject: RoomListElementComponent) => {
      roomObject.getRoomOccupation(startBooking, endBooking);
    });
  }

  public submitBooking(): void {}
}

function invalidBookingTimesValidator(): ValidatorFn {
  return (controls: AbstractControl): { [key: string]: any } | null => {
    const date = controls.get('date').value;
    let startBooking = controls.get('startBooking').value;
    let endBooking = controls.get('endBooking').value;

    startBooking = LocalDateTime.of(
      date.year,
      date.month,
      date.day,
      startBooking.hour,
      startBooking.minute);
    endBooking = LocalDateTime.of(
      date.year,
      date.month,
      date.day,
      endBooking.hour,
      endBooking.minute);

    const isInvalidStartTime = startBooking.isBefore(LocalDateTime.now()) || endBooking.isBefore(startBooking);

    return isInvalidStartTime ? {invalidBookingTimes: {value: startBooking}} : null;
  };
}
