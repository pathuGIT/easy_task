import { Routes } from '@angular/router';
import { LoginComponent } from './features/login.component/login.component';
import { Home } from './features/home/home';
import { RegisterComponent } from './features/register.component/register.component';

export const routes: Routes = [
    {path:'', component: Home},
    {path:'login', component:LoginComponent},
    {path:'register', component:RegisterComponent},
    {path: '**', redirectTo: '' }// fallback{}
];
