package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.*;
import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.hibernate.PropertyValueException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class BusinessServices {

    @Autowired
    ProfileDAO profileDAO;

    @Autowired
    TaskDAO taskDAO;

    @Autowired
    UserDAO userDAO;

    public BusinessServices() {
    }

    @PostConstruct
    public void init() {
        System.out.println("BusinessServices created");
    }

    public User createUser (User user) throws UserException {
        if (user == null) {
            throw new UserException("User is null");
        }
        if (user.getId() != null) {
            throw new UserException("User already exists");
        }
        try {
            userDAO.save(user);
        } catch (Exception e) {
            throw new UserException("User could not be created");
        }
        return user;
    }

    public User getUser(Long id) throws UserException {
        if (id == null) {
            throw new UserException("Id is null");
        }
        Optional<User> user = userDAO.findById(id);
        if (!user.isPresent()) {
            throw new UserException("User not found");
        }
        return user.get();
    }

    public User deleteUser(Long id) throws UserException {
        if (id == null) {
            throw new UserException("Id is null");
        }
        Optional<User> user = userDAO.findById(id);
        if (!user.isPresent()) {
            throw new UserException("User not found");
        }
        try {
            userDAO.delete(user.get());
        } catch (Exception e) {
            throw new UserException("User could not be deleted");
        }
        return user.get();
    }
}
