import {Room} from './room';
import {LocalDate, LocalDateTime} from '@js-joda/core';
import {User} from './user';

export class Booking {
  public id: number;
  public purpose: string;
  public date: LocalDate;
  public startBooking: LocalDateTime;
  public endBooking: LocalDateTime;
  public room: Room;
  public appUser: User;
}
