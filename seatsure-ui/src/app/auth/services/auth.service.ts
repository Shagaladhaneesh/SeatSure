import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { LoginRequest } from '../models/login-request';
import { LoginResponse } from '../models/login-response';
import { RegisterRequest } from '../models/register-request';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private http = inject(HttpClient);

  private readonly API = `${environment.apiUrl}/api/auth`;

  login(request: LoginRequest): Observable<LoginResponse> {

    return this.http.post<LoginResponse>(
      `${this.API}/login`,
      request
    );

  }

  register(request: RegisterRequest) {

    return this.http.post(

      `${this.API}/register`,

      {

        ...request,

        role: 'USER'

      }

    );

  }

  saveToken(token: string): void {

    localStorage.setItem("token", token);

  }

  getToken(): string | null {

    return localStorage.getItem("token");

  }

  logout(): void {

    localStorage.removeItem("token");

  }

  getMovies() {

    return this.http.get(`${environment.apiUrl}/api/movies`);

  }

}
