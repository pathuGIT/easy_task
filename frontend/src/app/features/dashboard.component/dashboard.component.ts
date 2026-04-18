import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { RouterLink } from "@angular/router";

@Component({
  selector: 'app-dashboard.component',
  imports: [RouterLink],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit{
  private auth = inject(AuthService);
  private cd = inject(ChangeDetectorRef);
  
  name: string = '';
  id: string = '';

  ngOnInit(){
    this.auth.profile().subscribe({
      next: (res)=>{
        this.name = res.data.username;
        this.id = res.data.id;
        this.cd.detectChanges();
      },
      error: (err)=>{
        console.log("err: "+ err.error?.message);
      }
    })
  }


}
