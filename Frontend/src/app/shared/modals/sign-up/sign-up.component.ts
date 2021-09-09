import { Component, OnInit } from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {User} from '../../models/user';
import {UserService} from '../../services/user.service';
import {ToastService} from '../../services/toast.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

  public userForm: FormGroup;

  constructor(
    public activeModal: NgbActiveModal,
    public fb: FormBuilder,
    private userService: UserService,
    private toastService: ToastService) { }

  public ngOnInit(): void {
    this.initForm();
  }

  public initForm(): void {
    this.userForm = this.fb.group({
      firstName: ['', [
        Validators.required,
        Validators.maxLength(100)
      ]],
      secondName: ['', [
        Validators.required,
        Validators.maxLength(100)
      ]],
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(8)
      ]],
    });
  }

  public submitUserData(): void {
    const userObject = this.getUserObject();

    this.userService.createUser(userObject).subscribe(
      (createdUser: User) => {
        if (createdUser.id != null) {
          this.showSuccessToast(`${createdUser.firstName}, you have been registered.`);
          this.activeModal.close();
          return;
        }

        this.showErrorToast('You could not be registered. Please try again.');
      },
      error => {
        if (error.error.httpStatus === 'UNPROCESSABLE_ENTITY') {
          this.showErrorToast(error.error.message);
          return;
        }
        this.showErrorToast('You could not be registered. Please try again.');
      });
  }

  public showSuccessToast(message: string): void {
    this.toastService.showSuccessToast(message);
  }

  public showErrorToast(message: string): void {
    this.toastService.showErrorToast(message);
  }

  public getUserObject(): User {
    const user = new User();

    user.firstName = this.userForm.controls.firstName.value;
    user.secondName = this.userForm.controls.secondName.value;
    user.email = this.userForm.controls.email.value;
    user.password = this.userForm.controls.password.value;

    return user;
  }
}
