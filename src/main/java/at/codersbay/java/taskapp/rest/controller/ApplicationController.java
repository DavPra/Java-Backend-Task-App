package at.codersbay.java.taskapp.rest.controller;

import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;
import at.codersbay.java.taskapp.rest.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApplicationController {

    @Autowired
    private UserServices UserServices;

    @Autowired
    private ProfileServices ProfileServices;

    @Autowired
    private TaskServices TaskServices;

    @GetMapping("/hi")
    String welcome() {
        return "Hello";
    }

    @GetMapping("/error")
    String error() {
        return "it gets hang to the error. Try enjoying surfing.";
    }

    @CrossOrigin
    @GetMapping("/users")
    List<User> getAllUsers() {
        return UserServices.getAllUsers();
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id) throws UserNotFoundException {
        return UserServices.getUserByID(id);
    }

    @GetMapping("/users/byEmail/{email}")
    User getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return UserServices.getUserByEmail(email);
    }

    @PostMapping("/users")
    User createUser(@RequestBody User user) throws UserAlreadyExistsException {

        HttpStatus status = null;
        String message = "";
        try {
            user = UserServices.createUser(user);
            status = HttpStatus.OK;
            message = "User created";
        } catch (UserAlreadyExistsException UAEE) {
            status = HttpStatus.CONFLICT;
            message = UAEE.getMessage();
        }
        return user;
    }

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException {
        return UserServices.updateUserByUserID(id, user);
    }

    @DeleteMapping("/users/{id}")
    User deleteUser(@PathVariable Long id) throws UserNotFoundException {
        return UserServices.deleteUser(id);
    }

    @GetMapping("/tasks")
    List<Task> getAllTasks() {
        return TaskServices.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable Long id) throws TaskNotFoundException {
        return TaskServices.getTaskByTaskID(id);
    }

    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task) throws TaskNotFoundException {
        return TaskServices.createTask(task);
    }

    @PutMapping("/tasks/{id}")
    Task updateTask(@PathVariable Long id, @RequestBody Task task) throws TaskNotFoundException {
        return TaskServices.updateTaskByTaskID(id, task);
    }

    @DeleteMapping("/tasks/{id}")
    Task deleteTask(@PathVariable Long id) throws TaskNotFoundException {
        return TaskServices.deleteTaskByTaskID(id);
    }

    @GetMapping("/profiles")
    List<Profile> getAllProfiles() {
        return ProfileServices.getAllProfiles();
    }

    @GetMapping("/profiles/{id}")
    Profile getProfile(@PathVariable Long id) throws ProfileNotFoundException {
        return ProfileServices.getProfileByUserID(id);
    }

    @PostMapping("/profiles")
    Profile createProfile(@RequestBody Profile profile) throws ProfileNotFoundException {
        return ProfileServices.createProfile(profile);
    }

    @PutMapping("/profiles/{id}")
    Profile updateProfile(@PathVariable Long id, @RequestBody Profile profile) throws ProfileNotFoundException {
        return ProfileServices.updateProfileByUserID(id, profile);
    }

    @DeleteMapping("/profiles/{id}")
    Profile deleteProfile(@PathVariable Long id) throws ProfileNotFoundException {
        return ProfileServices.deleteProfileByID(id);
    }


}
