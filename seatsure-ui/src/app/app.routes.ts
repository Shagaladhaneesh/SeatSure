import { Routes } from '@angular/router';
import {authGuard} from './shared/guards/auth-guard'
import { LoginComponent } from './auth/components/login/login.component';
import { DashboardComponent } from './dashboard/components/dashboard/dashboard';
import { MovieListComponent } from './movie/components/movie-list/movie-list';
import { ShowListComponent } from './show/components/show-list/show-list';
import { SeatSelectionComponent} from './show-seat/components/seat-selection/seat-selection';
import { BookingHistoryComponent} from './booking/components/booking-history/booking-history'
import { RegisterComponent } from './auth/components/register/register';
export const routes: Routes = [

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    component: LoginComponent
  },

  {
    path: 'dashboard',
    component: DashboardComponent
  },
{
    path: 'movies',
    component: MovieListComponent,
    canActivate: [authGuard]
},
{
    path: 'movies/:movieId/shows',
    component: ShowListComponent,
    canActivate: [authGuard]
},
{
    path: 'shows/:showId/seats',
    component: SeatSelectionComponent,
    canActivate: [authGuard]
},
{
    path: 'booking-history',
    component: BookingHistoryComponent,
    canActivate: [authGuard]
},
{
    path:'register',
    component:RegisterComponent
}
];
