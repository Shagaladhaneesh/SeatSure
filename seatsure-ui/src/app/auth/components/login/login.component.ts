import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router ,RouterLink} from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../services/auth.service';
import { LoginRequest } from '../../models/login-request';
//import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,RouterLink
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class LoginComponent {

  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router :Router
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

  const request: LoginRequest = this.loginForm.value;

  this.authService.login(request).subscribe({

    next: (response) => {

     this.authService.saveToken(response.token);
     this.router.navigate(['/dashboard']);

//      this.authService.getMovies().subscribe({
//
//        next: (movies) => {
//
//          console.log(movies);
//
//        },
//
//        error: (err) => {
//
//          console.error(err);
//
//        }
//
//      });

     console.log("Login Successful");
    },

    error: (error) => {

      console.error(error);

    }

  });

}

}
