package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.*;
import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import javax.annotation.PostConstruct;

@Service
public class UserServices {

    public UserServices() {
    }

    @Autowired
    UserDAO userDAO;

    public User createUser (User user) throws UserAlreadyExistsException {
        if (user == null) {
            throw new UserAlreadyExistsException("User is null");
        }
        if (user.getId() != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        try {
            userDAO.save(user);
        } catch (Exception e) {
            throw new UserAlreadyExistsException("User could not be created");
        }
        return user;
    }

    public List<User> getAllUsers() {

        return userDAO.findAll();
    }

    public User getUserByID (Long id) throws UserNotFoundException {
        if (id == null) {
            throw new UserNotFoundException("Id is null");
        }
        Optional<User> user = userDAO.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }

    public User getUserByEmail (String email) throws UserNotFoundException {
        if (email == null) {
            throw new UserNotFoundException("Email is null");
        }
        Optional<User> user = Optional.ofNullable(userDAO.findByEmail(email));
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();
    }

    public User deleteUser(Long id) throws UserNotFoundException {
        if (id == null) {
            throw new UserNotFoundException("Id is null");
        }
        Optional<User> user = userDAO.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        try {
            userDAO.delete(user.get());
        } catch (Exception e) {
            throw new UserNotFoundException("User could not be deleted");
        }
        return user.get();
    }
}
