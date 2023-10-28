package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.TaskDAO;
import at.codersbay.java.taskapp.rest.DAO.UserDAO;
import at.codersbay.java.taskapp.rest.entities.Task;
import at.codersbay.java.taskapp.rest.entities.User;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    private final UserDAO userDAO;
    private final TaskDAO taskDAO;

    @Autowired
    public UserServices(UserDAO userDAO, TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.taskDAO = taskDAO;
    }

    public User createUser(@RequestBody User user) throws UserAlreadyExistsException {
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

    public User deleteUser(Long userId) throws UserNotFoundException {
        User user = userDAO.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        for (Task task : user.getTasks()) {
            task.getUsers().remove(user);
            taskDAO.save(task); // Update the task
        }
        user.setTasks(null); // Remove all associations from the user side
        userDAO.delete(user);

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