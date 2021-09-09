import { Injectable } from '@angular/core';
import { Room } from '../models/room';
import { Observable } from 'rxjs';
import {RestService} from './rest.service';
import {LocalDateTime} from '@js-joda/core';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

  private endpoint = '/rooms';

  constructor(private restService: RestService) { }

  public createRoom(body: Room): Observable<Room> {
    return this.restService.post(this.endpoint, body) as Observable<Room>;
  }

  public findAllRooms(order: string): Observable<Array<Room>> {
    return this.restService.get(this.endpoint, [{name: 'order', value: order}]) as Observable<Array<Room>>;
  }

  public checkRoomForDependencies(id: number): Observable<boolean> {
    return this.restService.get(this.endpoint + '/has-dependencies', [{name: 'id', value: id.toString()}]) as Observable<boolean>;
  }

  public findRoomById(id: number): Observable<Room> {
    return this.restService.get(this.endpoint + '/' + id) as Observable<Room>;
  }

  public updateRoom(body: Room): Observable<Room> {
    return this.restService.put(this.endpoint + '/' + body.id, body) as Observable<Room>;
  }

  public deleteRoom(id: number): Observable<number> {
    return this.restService.delete(this.endpoint + '/' + id) as Observable<number>;
  }
}
