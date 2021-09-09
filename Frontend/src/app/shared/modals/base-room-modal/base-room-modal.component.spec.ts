import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BaseRoomModalComponent } from './base-room-modal.component';

describe('BaseRoomModalComponent', () => {
  let component: BaseRoomModalComponent;
  let fixture: ComponentFixture<BaseRoomModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BaseRoomModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BaseRoomModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
