import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Movie } from '../models/movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private http = inject(HttpClient);

  private readonly API = `${environment.apiUrl}/api/movies`;

  getMovies(): Observable<Movie[]> {

    return this.http.get<Movie[]>(this.API);

  }

}
