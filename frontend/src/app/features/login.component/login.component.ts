import { Component, inject } from '@angular/core';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../services/auth-service';
import { NgClass } from "../../../../node_modules/@angular/common/types/_common_module-chunk";
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login.component',
  imports: [RouterLink, FormsModule],
  templateUrl: './login.component.html',
})
export class LoginComponent {
  private router = inject(Router)
  constructor(private authService : AuthService){}

  username: string = '';
  password: string = '';

  login(){
    this.authService.login(this.username, this.password).subscribe({
      next: (req)=>{
        console.log("msg: "+ req.message);
        this.router.navigate(['/dashboard']); 
      },
      error: (err)=>{
        console.log("Login err: "+ err)
      }
    })
  }
}
