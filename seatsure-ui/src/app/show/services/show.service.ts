import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

import { Show } from '../models/show';

@Injectable({
  providedIn: 'root'
})
export class ShowService {

  private http = inject(HttpClient);

  private readonly API =
  `${environment.apiUrl}/shows`;

  getShowsByMovie(movieId:number):Observable<Show[]>{

      return this.http.get<Show[]>(
        `${this.API}/movie/${movieId}`
      );

  }

}
