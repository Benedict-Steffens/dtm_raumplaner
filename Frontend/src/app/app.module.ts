import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule, ROUTINGCOMPONENTS} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {RoomService} from './shared/services/room.service';
import {RoomElementComponent} from './ui-components/room-element/room-element.component';
import {NavbarComponent} from './ui-components/navbar/navbar.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {TimeConversionPipe} from './shared/pipes/time-conversion.pipe';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ToastComponent} from './ui-components/toast/toast.component';
import {CreateRoomModalComponent} from './shared/modals/base-room-modal/create-room-modal.component';
import {UpdateRoomModalComponent} from './shared/modals/base-room-modal/update-room-modal.component';
import {BaseRoomModalComponent} from './shared/modals/base-room-modal/base-room-modal.component';
import {DisplayRoomModalComponent} from './shared/modals/base-room-modal/display-room-modal.component';
import {BookingsComponent} from './ui-components/bookings/bookings.component';
import {BookingElementComponent} from './ui-components/booking-element/booking-element.component';
import {ModalContainerComponent} from './shared/modals/modal-container/modal-container.component';
import {BaseBookingModalComponent} from './shared/modals/base-booking-modal/base-booking-modal.component';
import {CreateBookingModalComponent} from './shared/modals/base-booking-modal/create-booking-modal.component';
import {Ng5SliderModule} from 'ng5-slider';
import {RoomListElementComponent} from './shared/modals/base-booking-modal/room-list-element/room-list-element.component';
import {LoginComponent} from './ui-components/login/login.component';
import {SignUpComponent} from './shared/modals/sign-up/sign-up.component';
import {AuthService} from './shared/security/auth.service';
import {TokenInterceptor} from './shared/security/token-interceptor';
import {AuthGuard} from './shared/security/auth-guard';

@NgModule({
  declarations: [
    AppComponent,
    ROUTINGCOMPONENTS,
    RoomElementComponent,
    NavbarComponent,
    TimeConversionPipe,
    ToastComponent,
    CreateRoomModalComponent,
    UpdateRoomModalComponent,
    BaseRoomModalComponent,
    DisplayRoomModalComponent,
    BookingsComponent,
    BookingElementComponent,
    ModalContainerComponent,
    BaseBookingModalComponent,
    CreateBookingModalComponent,
    RoomListElementComponent,
    LoginComponent,
    SignUpComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    FontAwesomeModule,
    NgbModule,
    ReactiveFormsModule,
    Ng5SliderModule
  ],
  providers: [
    RoomService,
    AuthService,
    AuthGuard,
    {provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
