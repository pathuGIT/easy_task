import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ProjectService } from '../../../services/project-service';

@Component({
  selector: 'app-newproject.component',
  imports: [FormsModule],
  templateUrl: './newproject.component.html',
})
export class NewprojectComponent {
  projectTitle: string = '';

  constructor(private projectService: ProjectService){}

  createProject() {
    if (!this.projectTitle.trim()) {
      alert('Project title is required');
      return;
    }

    console.log('New Project:', this.projectTitle);

    // TODO: call API here
    this.projectService.create(this.projectTitle).subscribe({
      next: (res)=>{
        console.log(res.message)
      },
      error: (err)=>{
        console.log("Project Error:", err.error?.message);
      }
    })

    this.projectTitle = ''; // reset
  }
}
