import {Component, OnInit} from '@angular/core';

import {faSignOutAlt} from '@fortawesome/free-solid-svg-icons';
import {LocalDateTime} from '@js-joda/core';
import {AuthService} from '../../shared/security/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  public currentTime = LocalDateTime.now();
  public isMenuToggleClicked = false;

  public signOutIcon = faSignOutAlt;

  constructor(
    private authService: AuthService,
    private router: Router) { }

  public ngOnInit(): void {
    this.clockTimer();
  }

  public clockTimer(): void {
    setInterval(() => {
      this.currentTime = LocalDateTime.now();
    }, 10000);
  }

  public changeMenuToggledStatus(): void {
    this.isMenuToggleClicked = !this.isMenuToggleClicked;
  }

  public logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/');
  }

}
