import {Component, Input, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder} from '@angular/forms';
import {Room} from '../../models/room';
import {BaseRoomModalComponent} from './base-room-modal.component';

@Component({
  selector: 'app-display-room-modal',
  templateUrl: './base-room-modal.component.html',
  styleUrls: ['./base-room-modal.component.scss']
})
export class DisplayRoomModalComponent extends BaseRoomModalComponent implements OnInit {

  @Input()
  public room = new Room();

  public title = 'Room Information';

  public isNotInformationModal = false;

  constructor(public activeModal: NgbActiveModal,
              public fb: FormBuilder) {
    super(activeModal, fb);
  }

  public ngOnInit(): void {
    this.initForm();
    this.disableForm();
  }

  public disableForm(): void {
    this.roomForm.disable();
  }
}
