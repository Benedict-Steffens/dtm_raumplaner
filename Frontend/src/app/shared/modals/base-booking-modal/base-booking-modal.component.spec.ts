import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseBookingModalComponent } from './base-booking-modal.component';

describe('BaseBookingModalComponent', () => {
  let component: BaseBookingModalComponent;
  let fixture: ComponentFixture<BaseBookingModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BaseBookingModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseBookingModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
