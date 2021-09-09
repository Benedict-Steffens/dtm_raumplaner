import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Booking} from '../../shared/models/booking';
import {LocalDateTime} from '@js-joda/core';

@Component({
  selector: 'app-booking-element',
  templateUrl: './booking-element.component.html',
  styleUrls: ['./booking-element.component.scss']
})
export class BookingElementComponent implements AfterViewInit {

  @Input()
  public booking: Booking;

  @Input()
  public userId: number;

  @ViewChild('componentBody')
  public componentBody: ElementRef;

  public ngAfterViewInit(): void {
      this.setPosition();
  }

  public setPosition(): void {
    const startBooking = LocalDateTime.parse(this.booking.startBooking.toString());
    const endBooking = LocalDateTime.parse(this.booking.endBooking.toString());
    const startTime = startBooking.hour() * 60 + startBooking.minute();
    const endTime = endBooking.hour() * 60 + endBooking.minute();
    this.componentBody.nativeElement.style.width = `${(endTime - startTime) * 2}px`;
    this.componentBody.nativeElement.style.left = `${startTime * 2}px`;
  }
}
