import { Component, inject, signal } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { Header } from './shared/header/header';
import { Home } from './features/home/home';
import { Sidebar } from './shared/sidebar/sidebar';
import { AuthService } from './services/auth-service';
import { filter, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Header, Sidebar],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  private auth = inject(AuthService);
  private router = inject(Router);
  private destroy$ = new Subject<void>();

  isLoggin = false;

  ngOnInit() {
    console.log("Navbar initialized");
    this.updateLoginStatus();

    // Listen to navigation events to update login status
    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd),
        takeUntil(this.destroy$)
      )
      .subscribe(() => {
        this.updateLoginStatus();
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  updateLoginStatus(): void {
    this.isLoggin = this.auth.isLoggedIn();
    console.log("Login status updated:", this.isLoggin);
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
