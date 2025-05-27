import { Component } from '@angular/core';
import {RouterOutlet} from "@angular/router";
import {MenuComponent} from "../../components/menu/menu.component";
import {User, UserService} from "../../../../services/services/user.service";
import {TokenService} from "../../../../services/token/token.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [
    RouterOutlet,
    MenuComponent,
    NgIf
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {


  currentUserFromToken: any;
  fullUser: User | null = null;

  constructor(
    private tokenService: TokenService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.currentUserFromToken = this.tokenService.getUserEmail();

    if (this.currentUserFromToken) {
      this.userService.getUserByEmail(this.currentUserFromToken)
        .subscribe({
          next: user => this.fullUser = user,
          error: err => console.error('User fetch error:', err)
        });

    }
  }
}
