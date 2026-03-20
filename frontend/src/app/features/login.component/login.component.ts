import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../services/auth-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login.component',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  private router = inject(Router)
  private cd = inject(ChangeDetectorRef);
  constructor(private authService: AuthService) { }

  username: string = '';
  password: string = '';
  warning: string[] = [];

  login() {
    this.authService.login(this.username, this.password).subscribe({
      next: (req) => {
        console.log("msg: " + req.message);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.log("Login err: " + err.error?.message)
        this.warning = [
          "Login Failed!!",
          "Check your Credentials again..."
        ];

        this.cd.detectChanges();

        setTimeout(() => {
          this.warning = [];
          this.cd.detectChanges();
        }, 3000);
      }
    })
  }
}
