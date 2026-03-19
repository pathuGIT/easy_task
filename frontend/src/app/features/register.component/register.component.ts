import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from "@angular/router";
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-register.component',
  imports: [RouterLink, FormsModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  private router = inject(Router)
  constructor(private authService : AuthService){}

  username: string = '';
  password: string = '';

  register(){
    this.authService.register(this.username, this.password).subscribe({
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
