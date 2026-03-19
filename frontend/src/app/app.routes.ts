import { Routes } from '@angular/router';
import { LoginComponent } from './features/login.component/login.component';
import { Home } from './features/home/home';
import { RegisterComponent } from './features/register.component/register.component';
import { AuthGuard } from './guards/auth.guard';
import { DashboardComponent } from './features/dashboard.component/dashboard.component';
import { TaskComponent } from './features/task.component/task.component';
import { NewtaskComponent } from './features/newtask.component/newtask.component';
import { UpadtetaskComponent } from './features/upadtetask.component/upadtetask.component';

export const routes: Routes = [
    {path:'', component: Home},
    {path:'login', component:LoginComponent},
    {path:'register', component:RegisterComponent},
    {path:'dashboard', component:DashboardComponent, canActivate: [AuthGuard]},
    {path:'task', component:TaskComponent, canActivate: [AuthGuard]},
    {path:'newtask', component:NewtaskComponent, canActivate: [AuthGuard]},
    {path:'updatetask/:id', component:UpadtetaskComponent, canActivate: [AuthGuard]},
    {path: '**', redirectTo: '' }// fallback{}
];
