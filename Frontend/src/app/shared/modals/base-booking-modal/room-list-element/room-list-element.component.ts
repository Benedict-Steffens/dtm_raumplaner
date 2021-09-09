import {Component, ElementRef, Input, OnInit, ViewChild} from '@angular/core';
import {Room} from '../../../models/room';
import {LocalDateTime} from '@js-joda/core';
import {BookingService} from '../../../services/booking.service';

@Component({
  selector: 'app-room-list-element',
  templateUrl: './room-list-element.component.html',
  styleUrls: ['./room-list-element.component.scss']
})
export class RoomListElementComponent implements OnInit {

  @Input()
  public room: Room;

  @ViewChild('checkBox')
  public checkBox: ElementRef;

  public startBooking: LocalDateTime;
  public endBooking: LocalDateTime;

  public isAvailableForSpecifiedPeriod = true;

  constructor(private bookingService: BookingService) { }

  public ngOnInit(): void {
  }

  public getRoomOccupation(startBooking: LocalDateTime, endBooking: LocalDateTime): void {
    this.startBooking = startBooking;
    this.endBooking = endBooking;

    this.bookingService.getRoomAvailability(this.room.id, startBooking, endBooking).subscribe(
      (isAvailable: boolean) => {
        this.isAvailableForSpecifiedPeriod = isAvailable;

        if (!isAvailable) {
          this.checkBox.nativeElement.checked = false;
        }
      }
    );
  }

  public getIfRadioBtnChecked(): boolean {
    return this.checkBox.nativeElement.checked;
  }
}
