// import { Component } from '@angular/core';
//
// @Component({
//   selector: 'app-home',
//   standalone: true,
//   imports: [],
//   templateUrl: './home.component.html',
//   styleUrl: './home.component.css'
// })
// export class HomeComponent {
//
// }

import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {NgForOf, NgIf} from "@angular/common";
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  isMenuOpen = false;
  constructor(private router: Router) {}

  toggleMenu(): void {
    this.isMenuOpen = !this.isMenuOpen;
  }

  onGetStarted(): void {
    this.router.navigate(['/register']);
  }

  onLogin(): void {
    this.router.navigate(['/login']);
  }
  onStartFreeTrial(): void {
    // Add your navigation logic here
    console.log('Start Free Trial clicked');
  }
  scrollTo(sectionId: string) {
    const yOffset = -80; // adjust based on your navbar height
    const element = document.getElementById(sectionId);
    if (element) {
      const y = element.getBoundingClientRect().top + window.scrollY + yOffset;
      window.scrollTo({ top: y, behavior: 'smooth' });
    }
  }
}
