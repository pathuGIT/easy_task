package com.easytask.backend.models;

import com.easytask.backend.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pId;

    @NotBlank
    @Column(unique = true)
    private String title;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private ProjectStatus proStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users users;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public Project() {
    }

    public Project(Long pId, String title, ProjectStatus proStatus, Users users, List<Task> tasks) {
        this.pId = pId;
        this.title = title;
        this.proStatus = proStatus;
        this.users = users;
        this.tasks = tasks;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProjectStatus getProStatus() {
        return proStatus;
    }

    public void setProStatus(ProjectStatus proStatus) {
        this.proStatus = proStatus;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
