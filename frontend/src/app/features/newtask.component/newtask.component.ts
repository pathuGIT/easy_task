import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { TaskService } from '../../services/task-service';
import { FormsModule } from '@angular/forms';
import { TaskRequest } from '../../interfaces/taskReqest';

@Component({
  selector: 'app-newtask.component',
  imports: [FormsModule],
  templateUrl: './newtask.component.html',
})
export class NewtaskComponent {
  private router = inject(Router);
  private taskService = inject(TaskService);
  private cd = inject(ChangeDetectorRef);

  title: string = '';
  description: string = '';
  status: string = 'TODO';
  warning: string = '';

  createTask() {
    const task: TaskRequest = {
      title: this.title,
      description: this.description,
      status: this.status
    };

    this.taskService.addTask(task).subscribe({
      next: (res) => {
        console.log("Create task success:", res);
        this.router.navigate(['/task']);
      },
      error: (err) => {
        console.log("Create task err:", err.error?.message);
        this.warning = err.error?.message;
        this.cd.detectChanges();

        setTimeout(() => {
          this.warning = '';
          this.cd.detectChanges();
        }, 3000);
      }
    });
  }
}
