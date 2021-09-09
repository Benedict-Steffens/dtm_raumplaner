import { Injectable } from '@angular/core';
import {NgbDate} from '@ng-bootstrap/ng-bootstrap';
import {LocalDate} from '@js-joda/core';

@Injectable({
  providedIn: 'root'
})
export class DataConversionService {

  constructor() { }

  public convertNgbDateToLocalDate(date: NgbDate): LocalDate {
    return LocalDate.of(date.year, date.month, date.day);
  }

  public convertLocalDateToNgbDate(date: LocalDate): NgbDate {
    return new NgbDate(date.year(), date.monthValue(), date.dayOfMonth());
  }
}
