import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/login-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    RouterLink
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  loginForm: FormGroup;

  errorMessage = '';
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {

    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });

  }

  login() {

    if (this.loginForm.invalid) {
      return;
    }

    this.errorMessage = '';
    this.isLoading = true;

    const request: LoginRequest = this.loginForm.value;

    this.authService.login(request).subscribe({

      next: (response) => {

        this.authService.saveToken(response.token);

        this.isLoading = false;

        console.log("Login Successful");

        this.router.navigate(['/dashboard']);

      },

      error: (error) => {

        this.isLoading = false;

        switch (error.status) {

          case 401:
          case 403:
            this.errorMessage = 'Invalid email or password.';
            break;

          case 0:
            this.errorMessage = 'Unable to connect to the server.';
            break;

          default:
            this.errorMessage = 'Something went wrong. Please try again.';
        }

        console.error(error);

      }

    });

  }

}
