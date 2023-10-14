package at.codersbay.java.taskapp.rest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * This entity is used to store the profile data in the database.
 * Each Profile has an ID, a bio, and an image.
 */
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "bio", nullable = false)
    private String bio;

    @Column(name = "image", nullable = false)
    private String image;

    @JsonIgnore
    @OneToOne(mappedBy = "profile", cascade = CascadeType.REMOVE)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
