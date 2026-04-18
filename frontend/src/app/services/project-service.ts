import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { NewProjectResponse } from '../interfaces/projectResponse';
import { environment } from '../../environement/environemnt';


@Injectable({
  providedIn: 'root',
})
export class ProjectService {
    private apiUrl = environment.api;
    default: string = "PLANNED";

    constructor(private http: HttpClient) {}

    create(title: string): Observable<NewProjectResponse>{
        console.log(title)
        return this.http
            .post<NewProjectResponse>(`${this.apiUrl}/project`,{title});
    }
}