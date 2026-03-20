import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../services/auth-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register.component',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  private router = inject(Router)
  private cd = inject(ChangeDetectorRef);
  constructor(private authService: AuthService) { }

  username: string = '';
  password: string = '';
  warning: string[] = [];

  register() {
    this.authService.register(this.username, this.password).subscribe({
      next: (req) => {
        console.log("msg: " + req.message);
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        console.log("Login err: " + err.error.message)
        if (err.error.message == "This username already exist...") {
          this.warning = [
            "Registration Failed!!",
            "Try with different username"
          ];
        } else {
          this.warning = [
            "Registration Failed!!",
            `${err.error.message}`
          ];
        }
        this.cd.detectChanges();

        setTimeout(() => {
          this.warning = [];
          this.cd.detectChanges();
        }, 3000);

      }
    })
  }

}
