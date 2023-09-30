package at.codersbay.java.taskapp.rest.entities;

import javax.persistence.*;

@Entity
@Table(name = "tasks")

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "Titel", nullable = false)
    private String titel;

    @Column(name = "Beschreibung", nullable = false)
    private String beschreibung;

    @Column(name = "Fälligkeitsdatum", nullable = false)
    private String dueDate;

    @Column(name = "Erledigt", nullable = false)
    private boolean done;

    public Long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
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
}

/* This entity is used to store the task data in the database.
* Each Task has an ID, a title, a description, a due date and a boolean to check if the task is done.
 */