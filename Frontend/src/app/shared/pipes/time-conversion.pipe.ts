import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'timeConversion'
})
export class TimeConversionPipe implements PipeTransform {
  public transform(time: number): string {
    if (time > 1440) {
      return `more than 24h`;
    }

    if (time < 60) {
      return `${time}min`;
    }

    const hours = time / 60;
    const minutes = time % 60;
    return `${Math.floor(hours)}h${minutes}min`;
  }
}
