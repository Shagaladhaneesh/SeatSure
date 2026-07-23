import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../models/register-request';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class RegisterComponent {

  private fb = inject(FormBuilder);

  private authService = inject(AuthService);

  private router = inject(Router);

  registerForm = this.fb.group({

    name: ['', Validators.required],

    email: ['', [Validators.required, Validators.email]],

    password: ['', Validators.required]

  });

  register() {

    if (this.registerForm.invalid) {

      return;

    }

    const request =
      this.registerForm.value as RegisterRequest;

    this.authService.register(request)
      .subscribe({

        next: () => {

          alert("Registration Successful!");

          this.router.navigate(['/login']);

        },

        error: err => {

          console.error(err);

          alert("Registration Failed");

        }

      });

  }

}
