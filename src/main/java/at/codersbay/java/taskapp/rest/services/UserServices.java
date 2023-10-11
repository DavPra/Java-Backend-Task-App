package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.UserDAO;
import at.codersbay.java.taskapp.rest.entities.User;
import at.codersbay.java.taskapp.rest.exceptions.UserAlreadyExistsException;
import at.codersbay.java.taskapp.rest.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final UserDAO userDAO;

    @Autowired
    public UserServices(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (user.getId() != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
        return userDAO.save(user);
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User getUserByID(Long id) throws UserNotFoundException {
        if (id == null) {
            throw new UserNotFoundException("Id is null");
        }
        return userDAO.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new UserNotFoundException("Email is null or empty");
        }
        return Optional.ofNullable(userDAO.findByEmail(email))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User deleteUser(Long id) throws UserNotFoundException {
        User user = getUserByID(id);
        try {
            userDAO.delete(user);
        } catch (Exception e) {
            throw new UserNotFoundException("User could not be deleted");
        }
        return user;
    }

    public User updateUserByUserID(Long id, User user) throws UserNotFoundException {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        User existingUser = getUserByID(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setProfile(user.getProfile());
        return userDAO.save(existingUser);
    }
}