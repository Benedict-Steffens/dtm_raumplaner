import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Room} from '../../models/room';

@Component({
  templateUrl: './base-room-modal.component.html',
  styleUrls: ['./base-room-modal.component.scss']
})
export class BaseRoomModalComponent implements OnInit {

  public room = new Room();

  public roomForm: FormGroup;

  public title: string;
  public btnInscription: string;

  public isNotInformationModal: boolean;

  public isNameAlreadyUsed = false;

  constructor(
    public activeModal: NgbActiveModal,
    public fb: FormBuilder) { }

  public ngOnInit(): void {
    this.initForm();
  }

  public submitRoom(): void { }

  public resetNameToNotUsed(): void {
    if (this.isNameAlreadyUsed) {
      this.roomForm.get('name').setErrors(null);
      this.isNameAlreadyUsed = false;
    }
  }

  public initForm(): void {
    this.roomForm = this.fb.group({
      name: [this.room.name, [
        Validators.required,
        Validators.maxLength(100)
      ]],
      description: [this.room.description, [
        Validators.required,
        Validators.maxLength(3000)
      ]],
      location: [this.room.location, [
        Validators.required,
        Validators.maxLength(500)
      ]],
      capacity: [this.room.capacity, [
        Validators.required,
        Validators.pattern('^[0-9]*$'),
        Validators.min(1)
      ]]
    });
  }

  public getRoom(): Room {
    const room = new Room();

    room.name = this.roomForm.controls.name.value;
    room.description = this.roomForm.controls.description.value;
    room.location = this.roomForm.controls.location.value;
    room.capacity = this.roomForm.controls.capacity.value;

    return room;
  }

}
