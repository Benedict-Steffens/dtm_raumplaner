import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RoomsComponent} from './ui-components/rooms/rooms.component';
import {BookingsComponent} from './ui-components/bookings/bookings.component';
import {ModalContainerComponent} from './shared/modals/modal-container/modal-container.component';
import {LoginComponent} from './ui-components/login/login.component';
import {AuthGuard} from './shared/security/auth-guard';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent
  },
  {
    path: 'bookings',
    component: BookingsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'rooms',
    component: RoomsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: ':type/:id',
    component: ModalContainerComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const ROUTINGCOMPONENTS = [RoomsComponent];
