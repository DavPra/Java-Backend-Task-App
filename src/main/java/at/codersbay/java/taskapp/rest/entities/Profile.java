package at.codersbay.java.taskapp.rest.entities;


import javax.persistence.*;

@Entity
@Table(name = "profiles")

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")

    private Long id;

    @Column(name = "UserID", nullable = false)
    private Long userid;

    @Column(name = "Bio", nullable = false)
    private String bio;

    @Column(name = "Image", nullable = false)
    private String image;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getId() {
        return id;
    }

    public Long getUser() {
        return userid;
    }

    public void setUser(Long user) {
        this.userid = user;
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
}

/* This entity is used to store the profile data in the database.
    * Each Profile has an ID, a bio and an image.
 */
