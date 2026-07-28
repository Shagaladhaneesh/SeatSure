import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ShowSeat } from '../models/show-seat';

@Injectable({
  providedIn: 'root'
})
export class ShowSeatService {

  private http = inject(HttpClient);

  private readonly API =
  `${environment.apiUrl}/api/shows`;

  getSeats(showId:number):Observable<ShowSeat[]>{

      return this.http.get<ShowSeat[]>(
          `${this.API}/${showId}/seats`
      );

  }

}
