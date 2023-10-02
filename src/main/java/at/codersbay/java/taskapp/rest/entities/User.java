package at.codersbay.java.taskapp.rest.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Column(name = "Vorname", nullable = false)
    private String firstName;

    @Column(name = "Nachname", nullable = false)
    private String lastName;

    @Column(name = "Email", nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile", referencedColumnName = "id")
    private Profile profile;

    @ManyToMany
            @JoinTable(
                    name = "user_tasks",
                    joinColumns = @JoinColumn(name = "user_id"),
                    inverseJoinColumns = @JoinColumn(name = "task_id"))
    Set<Task> tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String vorname) {
        this.firstName = vorname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String nachname) {
        this.lastName = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", Vorname='" + firstName + '\'' +
                ", Nachname='" + lastName + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }


}

/* This entity is used to store the user data in the database.
* Each User has an ID, Vor and Nachname and an email adress.
* Getter and Setter allow further usage of the data.
* */
