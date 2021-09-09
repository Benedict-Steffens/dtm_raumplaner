import { Injectable } from '@angular/core';
import {RestService} from './rest.service';
import {User} from '../models/user';
import {Observable} from 'rxjs';
import {Credentials} from '../models/credentials';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private endpoint = '/users';

  constructor(private restService: RestService) { }

  public findUserById(id: number): Observable<User> {
    return this.restService.get(this.endpoint + '/' + id) as Observable<User>;
  }

  public createUser(body: User): Observable<User> {
    return this.restService.post(this.endpoint + '/sign-up', body) as Observable<User>;
  }

  public authenticateUser(credentials: Credentials): Observable<any> {
    const resp = this.restService.post('/authenticate', credentials, true) as Observable<any>;
    console.log(resp);
    return resp;
  }
}
