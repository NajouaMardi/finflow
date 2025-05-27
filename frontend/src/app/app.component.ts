import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {HttpClientModule} from "@angular/common/http";
import {NavbarComponent} from "./navbar/navbar.component";
import {TokenService} from "./services/token/token.service";
import {UserService} from "./services/services/user.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, HttpClientModule, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';



  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

}
