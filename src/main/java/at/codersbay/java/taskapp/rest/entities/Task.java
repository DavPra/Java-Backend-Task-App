package at.codersbay.java.taskapp.rest.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

/**
 * This entity is used to store the task data in the database.
 * Each Task has an ID, a title, a description, a due date, and a boolean to check if the task is done.
 */
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "due_date", nullable = false)
    private String dueDate;

    @Column(name = "is_done", nullable = false)
    private boolean done;

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.LAZY)
    @JsonIgnore
    @Column(name = "userIDs")
    private Set<User> users;

    public Task() {}

    public Task(String title, String description, String dueDate, boolean done, Set<User> users) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.done = done;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", done=" + done +
                ", userIDs=" + users +
                '}';
    }
}
