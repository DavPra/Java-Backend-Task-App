package at.codersbay.java.taskapp.rest.entities;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "Vorname", nullable = false)
    private String vorname;

    @Column(name = "Nachname", nullable = false)
    private String nachname;

    @Column(name = "Email", nullable = false)
    private String email;

    public Long getId() {
        return id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

/* This entity is used to store the user data in the database.
* Each User has an ID, Vor and Nachname and an email adress.
* Getter and Setter allow further usage of the data.
* */
