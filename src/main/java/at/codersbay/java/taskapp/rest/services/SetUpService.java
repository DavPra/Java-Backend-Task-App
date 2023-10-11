package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.*;
import at.codersbay.java.taskapp.rest.entities.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SetUpService {

    private final UserDAO userDAO;
    private final TaskDAO taskDAO;
    private final ProfileDAO profileDAO;

    @Autowired
    public SetUpService(UserDAO userDAO, TaskDAO taskDAO, ProfileDAO profileDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
        this.profileDAO = profileDAO;
    }

    @PostConstruct
    private void setUp() {
        User admin = new User();
        admin.setFirstName("David");
        admin.setLastName("Praschak");
        admin.setEmail("david.praschak@test.org");
        admin = this.userDAO.save(admin);

        User user = new User();
        user.setFirstName("Max");
        user.setLastName("Mustermann");
        user.setEmail("max@mustermann.org");
        user = this.userDAO.save(user);

        Task task1 = new Task();
        task1.setTitel("Task 1");
        task1.setBeschreibung("Beschreibung 1");
        task1.setDueDate("01.01.2020");
        task1.setDone(false);
        task1 = this.taskDAO.save(task1);

        Task task2 = new Task();
        task2.setTitel("Task 2");
        task2.setBeschreibung("Beschreibung 2");
        task2.setDueDate("02.02.2020");
        task2.setDone(false);
        task2 = this.taskDAO.save(task2);

        Profile profile1 = new Profile();
        profile1.setBio("Bio 1");
        profile1.setImage("Image 1");
        admin.setProfile(profile1);
        profile1.setUser(admin);
        profile1 = this.profileDAO.save(profile1);

        Profile profile2 = new Profile();
        profile2.setBio("Bio 2");
        profile2.setImage("Image 2");
        user.setProfile(profile2);
        profile2.setUser(user);
        profile2 = this.profileDAO.save(profile2);

    }
}
