import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

import { CreateBookingRequest } from '../models/create-booking-request';
import { BookingResponse } from '../models/booking-response';
import { BookingSummary } from '../models/booking-summary';

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  private http = inject(HttpClient);

  private readonly API =
   `${environment.apiUrl}/api/bookings`;

  bookSeats(request:CreateBookingRequest)
      :Observable<BookingResponse>{

      return this.http.post<BookingResponse>(
          this.API,
          request
      );

  }

  getMyBookings():Observable<BookingSummary[]>{

      return this.http.get<BookingSummary[]>(
          `${this.API}/my-bookings`
      );

  }

  cancelBooking(id:number){

      return this.http.patch(
          `${this.API}/${id}/cancel`,
          {}
      );

  }

}
