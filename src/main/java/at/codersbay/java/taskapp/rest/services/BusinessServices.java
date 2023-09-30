package at.codersbay.java.taskapp.rest.services;

import at.codersbay.java.taskapp.rest.DAO.*;
import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import javax.annotation.PostConstruct;

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

    public Task createTask (Task task) throws TaskNotFoundException {
        if (task == null) {
            throw new TaskNotFoundException("Task is null");
        }
        if (task.getId() != null) {
            throw new TaskNotFoundException("Task already exists");
        }
        try {
            taskDAO.save(task);
        } catch (Exception e) {
            throw new TaskNotFoundException("Task could not be created");
        }
        return task;
    }

    public Task getTaskByTaskID(Long id) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        Optional<Task> task = taskDAO.findById(id);
        if (!task.isPresent()) {
            throw new TaskNotFoundException("Task not found");
        }
        return task.get();
    }

    public Task deleteTaskByTaskID(Long id) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        Optional<Task> task = taskDAO.findById(id);
        if (!task.isPresent()) {
            throw new TaskNotFoundException("Task not found");
        }
        try {
            taskDAO.delete(task.get());
        } catch (Exception e) {
            throw new TaskNotFoundException("Task could not be deleted");
        }
        return task.get();
    }

    public Task updateTaskByTaskID(Long id, Task task) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        Optional<Task> taskToUpdate = taskDAO.findById(id);
        if (!taskToUpdate.isPresent()) {
            throw new TaskNotFoundException("Task not found");
        }
        if (task == null) {
            throw new TaskNotFoundException("Task is null");
        }
        if (task.getId() != null) {
            throw new TaskNotFoundException("Task already exists");
        }
        try {
            taskDAO.save(task);
        } catch (Exception e) {
            throw new TaskNotFoundException("Task could not be updated");
        }
        return task;
    }

   /* public List<Task> getTasksByUserID (Long id) throws TaskNotFoundException {
        if (id == null) {
            throw new TaskNotFoundException("Id is null");
        }
        List<Task> tasks = taskDAO.findByUserId(id);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Tasks not found");
        }
        return tasks;
    }
    */

}
