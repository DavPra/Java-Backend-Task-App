package at.codersbay.java.taskapp.rest.entities;


import javax.persistence.*;

@Entity
@Table(name = "profiles")

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")

    private Long id;

    @Column(name = "Bio", nullable = false)
    private String bio;

    @Column(name = "Image", nullable = false)
    private String image;

    @OneToOne(mappedBy = "profile")
    private User user;

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
}

/* This entity is used to store the profile data in the database.
    * Each Profile has an ID, a bio and an image.
 */
