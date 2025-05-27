import {Component, Input} from '@angular/core';
import {RouterLink, RouterOutlet} from "@angular/router";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [RouterOutlet, RouterLink],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {


  @Input() userId!: number;
  sidebarOpen = true;

  ngOnInit(): void {
    /*const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(link => {
      if (window.location.href.endsWith(link.getAttribute('href') || '')) {
        link.classList.add('active');
      }
      link.addEventListener('click', () => {
        linkColor.forEach(l => l.classList.remove('active'));
        link.classList.add('active');
      });
    });*/


  }

  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  logout() {

    localStorage.removeItem('token');
    window.location.reload();
  }
}
