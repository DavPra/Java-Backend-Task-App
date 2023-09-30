package at.codersbay.java.taskapp.rest.entities;

import jdk.jshell.Snippet;

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

}

/* This entity is used to store the profile data in the database.
    * Each Profile has an ID, a bio and an image.
 */
