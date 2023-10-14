package at.codersbay.java.taskapp.rest.DTO;

import java.util.Set;

public class TaskCreationDTO {
    private String title;
    private String description;
    private String dueDate;
    private boolean done;
    private Set<Long> userIDs;

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

    public Set<Long> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(Set<Long> userIDs) {
        this.userIDs = userIDs;
    }
}