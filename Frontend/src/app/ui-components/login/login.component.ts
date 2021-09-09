import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {SignUpComponent} from '../../shared/modals/sign-up/sign-up.component';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Credentials} from '../../shared/models/credentials';
import {UserService} from '../../shared/services/user.service';
import {take} from 'rxjs/operators';
import {Router} from '@angular/router';
import {AuthService} from '../../shared/security/auth.service';
import {ToastService} from '../../shared/services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public userCredentialsForm: FormGroup;

  constructor(
    private modalService: NgbModal,
    public fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private authService: AuthService,
    private toastService: ToastService) { }

  public ngOnInit(): void {
    this.initForm();
    this.checkIfLoggedIn();
  }

  public openSignUpModal(): void {
    this.modalService.open(SignUpComponent);
  }

  public initForm(): void {
    this.userCredentialsForm = this.fb.group({
      email: ['', [
        Validators.required,
        Validators.email
      ]],
      password: ['', [
        Validators.required,
        Validators.minLength(8)
      ]]
    });
  }

  public getCredentials(): Credentials {
    const credentials = new Credentials();

    credentials.email = this.userCredentialsForm.controls.email.value;
    credentials.password = this.userCredentialsForm.controls.password.value;

    return credentials;
  }

  public submitLoginData(): void {
    this.userService.authenticateUser(this.getCredentials()).pipe(take(1)).subscribe(
      resp => {
        this.authService.setToken(resp.headers.get('authorization'));
        this.toastService.showSuccessToast('You have been logged in successfully.');
        this.router.navigateByUrl('/bookings');
      }, error => {
        this.toastService.showErrorToast('Your Login was not successful. Please try again.');
      }
    );
  }

  private checkIfLoggedIn(): void {
    if (this.authService.isLoggedIn()) {
      this.router.navigateByUrl('/bookings');
    }
  }

}
