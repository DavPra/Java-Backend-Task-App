package at.codersbay.java.taskapp.rest.controller;

import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;
import at.codersbay.java.taskapp.rest.services.*;
import at.codersbay.java.taskapp.rest.DTO.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
        ProfileServices.deleteProfileByUserID(id);
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
    Task createTask(@RequestBody TaskCreationRequest taskRequest) throws TaskNotFoundException, UserNotFoundException {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(taskRequest.getDueDate());
        task.setDone(taskRequest.isDone());

        Set<User> users = taskRequest.getUserIds().stream()
                .map(userId -> {
                    try {
                        return UserServices.getUserByID(userId);
                    } catch (UserNotFoundException e) {
                        throw new RuntimeException("User not found for ID: " + userId);
                    }
                })
                .collect(Collectors.toSet());

        task.setUsers(users);
        return TaskServices.createTask(task, users);
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

    @PostMapping("/profiles/{id}")
    Profile createProfile(@RequestBody Profile profile) throws ProfileNotFoundException {
        ProfileServices.createProfile(profile);
        return ProfileServices.linkProfileIDtoUserID(profile.getUser().getId(), profile);
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
