import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environement/environemnt';
import { Observable } from 'rxjs';
import { TaskRequest } from '../interfaces/taskReqest';
import { TaskResponse } from '../interfaces/taskResponse';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private apiUrl = environment.api;

  constructor(private http: HttpClient){}

  //add task
  addTask(task: TaskRequest): Observable<TaskRequest>{
    console.log("llllllllllll"+ task);
    return this.http.post<TaskRequest>(`${this.apiUrl}/task/create`,task);
  }

  //get task by id
  getTaskById(id : number): Observable<TaskResponse>{
    return this.http.get<TaskResponse>(`${this.apiUrl}/task/${id}`);
  }

  //get all task
  getAllTask(): Observable<TaskResponse>{
    return this.http.get<TaskResponse>(`${this.apiUrl}/task/all`);
  }

  //update task
  updateTask(id: number, task: TaskRequest): Observable<TaskRequest>{
    return this.http.put<TaskRequest>(`${this.apiUrl}/task/${id}`,task);
  }

  //delete a task by id
  deleteTask(id : number): Observable<void>{
    console.group("Id: "+id)
    return this.http.delete<void>(`${this.apiUrl}/task/${id}`);
  }

}
