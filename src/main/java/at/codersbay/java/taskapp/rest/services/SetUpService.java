package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.*;
import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.PropertyValueException;

import java.util.*;
import javax.annotation.PostConstruct;

public class SetUpService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    TaskDAO taskDAO;

    @Autowired
    ProfileDAO profileDAO;

    public SetUpService() {
    }

    @PostConstruct
    public void setUp() {

        User admin = new User();
        admin.setVorname("David");
        admin.setNachname("Praschak");
        admin.setEmail("david.praschak@test.org");

        admin = this.userDAO.save(admin);

        User user = new User();
        user.setVorname("Max");
        user.setNachname("Mustermann");
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

    }

}