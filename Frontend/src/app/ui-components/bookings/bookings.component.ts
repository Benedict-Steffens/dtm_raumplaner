import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {faPlusCircle, faCaretSquareRight, faCaretSquareLeft} from '@fortawesome/free-solid-svg-icons';
import {LocalDate, LocalDateTime} from '@js-joda/core';
import {BookingService} from '../../shared/services/booking.service';
import {Booking} from '../../shared/models/booking';
import {Room} from '../../shared/models/room';
import {RoomService} from '../../shared/services/room.service';
import {NgbDate, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ToastService} from '../../shared/services/toast.service';
import {CreateBookingModalComponent} from '../../shared/modals/base-booking-modal/create-booking-modal.component';
import {DataConversionService} from '../../shared/services/data-conversion.service';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.scss']
})
export class BookingsComponent implements OnInit, AfterViewInit {

  public allBookingsOnSelectedDate: Array<Booking>;
  public filteredBookings: Array<Booking>;
  public allRooms: Array<Room>;

  public plusCircleIcon = faPlusCircle;
  public arrowRightIcon = faCaretSquareRight;
  public arrowLeftIcon = faCaretSquareLeft;

  public selectedDate: NgbDate;

  public hourItemsForTable = new Array(24);

  public userId = 1;

  @ViewChild('timetable')
  public timetable: ElementRef;

  @ViewChild('line')
  public line: ElementRef;

  @ViewChild('checkboxOwnBookings')
  public onlyOwnBookings: ElementRef;

  constructor(private bookingService: BookingService,
              private roomService: RoomService,
              private toastService: ToastService,
              private modalService: NgbModal,
              private dataConversionService: DataConversionService) { }

  public ngOnInit(): void {
    this.initSelectedDate();
    this.updateBookingAndRoomDate();
  }

  public ngAfterViewInit(): void {
    this.scrollDivToSpecificPosition();
    this.timeBarTimer();
  }

  public filterBookings(): void {
    if (this.onlyOwnBookings.nativeElement.checked) {
      this.filteredBookings = this.allBookingsOnSelectedDate.filter(
        (booking: Booking) => booking.appUser.id === this.userId
      );
      return;
    }

    this.filteredBookings = this.allBookingsOnSelectedDate;
  }

  public scrollDivToSpecificPosition(position?: number): void {
    if (position === undefined) {
      const timeNow = LocalDateTime.now();
      position = 2 * (timeNow.hour() * 60 + timeNow.minute()) - 120;
    }

    this.timetable.nativeElement.scrollLeft = 0;
    const timer = setInterval(() => {
      let isIfAlreadyChecked = false;
      if (isIfAlreadyChecked) {
        isIfAlreadyChecked = true;
      } else {
        this.timetable.nativeElement.scrollLeft = position;
        clearInterval(timer);
      }
    }, 100);
  }

  public initSelectedDate(): void {
    const dateToday = LocalDate.now();
    this.selectedDate = new NgbDate(dateToday.year(), dateToday.monthValue(), dateToday.dayOfMonth());
  }

  public timeBarTimer(): void {
    const currentTime = LocalDateTime.now();
    let timeBarPosition = 2 * (currentTime.hour() * 60 + currentTime.minute());
    this.line.nativeElement.style.left = `${timeBarPosition}px`;

    setInterval(() => {
      timeBarPosition += 2;
      this.line.nativeElement.style.left = `${timeBarPosition}px`;
    }, 60000);
  }

  public getAllRooms(): void {
    this.roomService.findAllRooms('asc').subscribe(
      (rooms: Array<Room>) => {
        this.allRooms = rooms;

        const height = this.allRooms.length * 70 - 7;
        this.line.nativeElement.style.height = `${height}px`;
      },
      error => {
        this.toastService.showErrorToast('Could not get room data from server.');
      }
    );
  }

  public updateBookingTable(): void {
    this.getBookingsOfSelectedDate();

    if (this.dataConversionService.convertNgbDateToLocalDate(this.selectedDate).equals(LocalDate.now())) {
      this.line.nativeElement.style.display = 'block';
      this.scrollDivToSpecificPosition();
      return;
    }

    this.line.nativeElement.style.display = 'none';
    this.scrollDivToSpecificPosition(960);
  }

  public getBookingsOfSelectedDate(): void {
    this.bookingService.findRoomsByDate(this.dataConversionService.convertNgbDateToLocalDate(this.selectedDate)).subscribe(
      (bookings: Array<Booking>) => {
        this.allBookingsOnSelectedDate = this.sortBookingsByStartTime(bookings);
        this.filterBookings();
      },
      error => {
        this.toastService.showErrorToast('Could not get booking data from server.');
      }
    );
  }

  public sortBookingsByStartTime(bookings: Array<Booking>): Array<Booking> {
    return bookings.sort((a: Booking, b: Booking) => (a.startBooking > b.startBooking) ? 1 : -1);
  }

  public changeSelectedDate(dateChange: number): void {
    const selectedDate: LocalDate = this.dataConversionService.convertNgbDateToLocalDate(this.selectedDate);
    this.selectedDate = this.dataConversionService.convertLocalDateToNgbDate(selectedDate.plusDays(dateChange));
    this.updateBookingTable();
  }

  public openCreateBookingModal(): void {
    const createBookingModalRef = this.modalService.open(CreateBookingModalComponent);
    createBookingModalRef.componentInstance.createBookingEvent.subscribe((createdBooking: Booking) => {
      this.showSuccessToast(`The booking ${createdBooking.purpose} was created!`);
      this.updateBookingAndRoomDate();
    });
  }

  public showSuccessToast(message: string): void {
    this.toastService.showSuccessToast(message);
  }

  public updateBookingAndRoomDate(): void {
    this.getBookingsOfSelectedDate();
    this.getAllRooms();
  }
}
