import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  private baseUrl = environment.backendUrl;

  constructor(private http: HttpClient) {
  }

  public createParams(params: Array<{ name: string, value: string }>): HttpParams {

    let httpParam = new HttpParams();

    params.forEach((param: { name: string, value: string }) => {
      httpParam = httpParam.set(param.name, param.value);
    });

    return httpParam;
  }

  public get<T>(endpoint: string, pathVariable?: Array<{ name: string, value: string }>): Observable<T> {
    if (pathVariable) {
      const params = this.createParams(pathVariable);

      return this.http.get(this.baseUrl + endpoint, {params}) as Observable<T>;
    }

    return this.http.get(this.baseUrl + endpoint) as Observable<T>;
  }

  public post<T>(endpoint: string, body: T, focus?: boolean): Observable<any> {
    if (focus) {
      return this.http.post(this.baseUrl + endpoint, body, {observe: 'response'}) as Observable<any>;
    }

    return this.http.post(this.baseUrl + endpoint, body) as Observable<any>;
  }

  public put<T>(endpoint: string, body: T): Observable<T> {
    return this.http.put(this.baseUrl + endpoint, body) as Observable<T>;
  }

  public delete(endpoint: string): Observable<number> {
    return this.http.delete(this.baseUrl + endpoint) as Observable<number>;
  }
}
