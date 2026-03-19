import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../../services/task-service';
import { FormsModule } from '@angular/forms';
import { TaskRequest } from '../../interfaces/taskReqest';

@Component({
  selector: 'app-upadtetask.component',
  imports: [FormsModule],
  templateUrl: './upadtetask.component.html',
})

export class UpadtetaskComponent implements OnInit {
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private taskService = inject(TaskService);
  private cd = inject(ChangeDetectorRef);

  taskId!: number;
  title: string = '';
  description: string = '';
  status: string = 'TO_DO';
  warning: string = '';

  ngOnInit() {
    this.taskId = Number(this.route.snapshot.paramMap.get('id'));
    if(this.taskId) {
      this.taskService.getTaskById(this.taskId).subscribe({
        next: (res: any) => {
          const task = res.data;
          this.title = task.title;
          this.description = task.description;
          this.status = task.status;
          this.cd.detectChanges();
        },
        error: (err) => {
          console.log("Load task error:", err);
          this.warning = "Failed to load task details";
        }
      });
    }
  }

  updateTask() {
    const task: TaskRequest = {
      title: this.title,
      description: this.description,
      status: this.status
    };

    this.taskService.updateTask(this.taskId, task).subscribe({
      next: (res) => {
        console.log("Update task success:", res);
        this.router.navigate(['/task']);
      },
      error: (err) => {
        console.log("Update task err:", err.error?.message);
        this.warning = err.error?.message || "Task update failed";
        this.cd.detectChanges();

        setTimeout(() => {
          this.warning = '';
          this.cd.detectChanges();
        }, 3000);
      }
    });
  }
}
