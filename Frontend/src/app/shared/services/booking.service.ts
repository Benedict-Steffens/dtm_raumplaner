import { Injectable } from '@angular/core';
import {Booking} from '../models/booking';
import {Observable} from 'rxjs';
import {RestService} from './rest.service';
import {LocalDate, LocalDateTime} from '@js-joda/core';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  private endpoint = '/bookings';

  public constructor(private restService: RestService) { }

  public createBooking(body: Booking): Observable<Booking> {
    return this.restService.post(this.endpoint, body) as Observable<Booking>;
  }

  public getOccupationDurationByRoomId(roomId: number): Observable<number> {
    return this.restService.get(this.endpoint + '/get-room-occupation', [{name: 'roomId', value: roomId.toString()}]) as Observable<number>;
  }

  public getRoomAvailability(roomId: number, startBooking: LocalDateTime, endBooking: LocalDateTime): Observable<boolean> {
    return this.restService.get(this.endpoint + '/room-availability',
      [{name: 'roomId', value: roomId.toString()},
        {name: 'startBooking', value: startBooking.toString()},
        {name: 'endBooking', value: endBooking.toString()}]) as Observable<boolean>;
  }

  public findRoomsByDate(date: LocalDate): Observable<Array<Booking>> {
    return this.restService.get(this.endpoint + '/by-date', [{name: 'date', value: date.toString()}]) as Observable<Array<Booking>>;
  }
}
