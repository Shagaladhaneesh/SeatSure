import { Component, inject } from '@angular/core';
import { Router, RouterLink ,RouterLinkActive} from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink,RouterLinkActive],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class NavbarComponent {

  private router = inject(Router);

   isLoginPage(): boolean {

      return this.router.url === '/login';

    }

  logout(){

    if(confirm("Logout?")){

      localStorage.clear();

      this.router.navigate(['/login']);

    }

  }
goHome() {

    if (localStorage.getItem('token')) {

        this.router.navigate(['/dashboard']);

    } else {

        this.router.navigate(['/login']);

    }

}
isLoggedIn(): boolean {

    return !!localStorage.getItem('token');

}

}
