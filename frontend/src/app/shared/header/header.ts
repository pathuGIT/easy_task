import { Component, inject, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterLink } from "@angular/router";
import { AuthService } from '../../services/auth-service';
import { filter, Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-header',
  imports: [RouterLink],
  templateUrl: './header.html',
})
export class Header implements OnInit {
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
