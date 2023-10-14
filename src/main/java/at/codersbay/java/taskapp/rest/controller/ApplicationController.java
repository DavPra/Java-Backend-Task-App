package at.codersbay.java.taskapp.rest.controller;

import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;
import at.codersbay.java.taskapp.rest.services.*;
import at.codersbay.java.taskapp.rest.DTO.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
public class ApplicationController {

    @Autowired
    private UserServices UserServices;

    @Autowired
    private ProfileServices ProfileServices;

    @Autowired
    private TaskServices TaskServices;

    // Testing paths
    @GetMapping("/hi")
    String welcome() {
        return "Hello";
    }

    @GetMapping("/error")
    String error() {
        return "it gets hang to the error. Try enjoying surfing.";
    }

    // User paths
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
        return UserServices.createUser(user);
    }

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException {
        return UserServices.updateUserByUserID(id, user);
    }

    @DeleteMapping("/users/{id}")
    User deleteUser(@PathVariable Long id) throws UserNotFoundException, ProfileNotFoundException {
        return UserServices.deleteUser(id);
    }

    // Task paths

    @GetMapping("/tasks")
    List<Task> getAllTasks() {
        return TaskServices.getAllTasks();
    }

    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable Long id) throws TaskNotFoundException {
        return TaskServices.getTaskByTaskID(id);
    }

    @PostMapping("/tasks")
    public Task createTask(@RequestBody TaskCreationDTO taskDTO) {
        try {
            return TaskServices.createTask(taskDTO);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "One or more users not found", e);
        } catch (TaskAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task Already exists", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating the task", e);
        }
    }


    @PutMapping("/tasks/{id}")
    Task updateTask(@PathVariable Long id, @RequestBody Task task) throws TaskNotFoundException {
        return TaskServices.updateTaskByTaskID(id, task);
    }

    @DeleteMapping("/tasks/{id}")
    Task deleteTask(@PathVariable Long id) throws TaskNotFoundException {
        return TaskServices.deleteTaskByTaskID(id);
    }

    // Profile paths
    @GetMapping("/profiles")
    List<Profile> getAllProfiles() {
        return ProfileServices.getAllProfiles();
    }

    @GetMapping("/profiles/{id}")
    Profile getProfile(@PathVariable Long id) throws ProfileNotFoundException {
        return ProfileServices.getProfileByUserID(id);
    }

    @PostMapping("/profiles/{userID}")
    public Profile createProfile(@PathVariable Long userID, @RequestBody Profile profile) throws PrimaryIdNullOrEmptyException, ProfileNotFoundException, UserNotFoundException, ProfileAlreadyExistsException {
        return ProfileServices.createAndLinkProfileToUser(userID, profile);
    }


    @PutMapping("/profiles/{id}")
    Profile updateProfile(@PathVariable Long id, @RequestBody Profile profile) throws ProfileNotFoundException {
        return ProfileServices.updateProfileByUserID(id, profile);
    }

    @DeleteMapping("/profiles/{id}")
    Profile deleteProfile(@PathVariable Long id) throws ProfileNotFoundException, UserNotFoundException {
        Profile profile = ProfileServices.getProfileByUserID(id);
        UserServices.deleteUser(profile.getUser().getId());
        return ProfileServices.deleteProfileByID(id);
    }


}
