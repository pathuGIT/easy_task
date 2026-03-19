import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { TaskService } from '../../services/task-service';
import { TaskResponse } from '../../interfaces/taskResponse';
import { Router, RouterLink } from "@angular/router";

interface taskList {
  "id": number,
  "title": string,
  "description": string,
  "status": string,
  "createdAt": Date,
};

@Component({
  selector: 'app-task.component',
  imports: [RouterLink],
  templateUrl: './task.component.html',
})
export class TaskComponent {
  private taskService = inject(TaskService);
  private cd = inject(ChangeDetectorRef);
  private router = inject(Router);

  taskList: taskList[] = [];
  message: string = '';

  ngOnInit() {
    this.loadAllTask();
  }

  loadAllTask() {
    this.taskService.getAllTask().subscribe({
      next: (res: TaskResponse) => {
        console.log(res.data)
        this.taskList = res.data;
        this.cd.detectChanges();
        console.log("task list:: ", this.taskList[0]?.description);
      },
      error: (err) => {
        console.log(console.log("Task Loading Err: " + err));
      }
    });
  }

  delete(id: number) {
    this.taskService.deleteTask(id).subscribe({
      next: (res) => {
        this.loadAllTask();

        setTimeout(() => {
        this.cd.detectChanges();
        }, 500);
      },
      error: (err) => {
        console.log("Deleted Failed task id: " + id);
      }
    })
  }

  edit(id: number) {
    this.router.navigate([`/updatetask/${id}`])
  } 


}
