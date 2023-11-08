package at.codersbay.java.taskapp.rest.controller;

import at.codersbay.java.taskapp.rest.entities.*;
import at.codersbay.java.taskapp.rest.exceptions.*;
import at.codersbay.java.taskapp.rest.services.*;
import at.codersbay.java.taskapp.rest.DTO.*;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class ApplicationController {

    //public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";

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
    @GetMapping("/users")  //returns all users in the database
    List<User> getAllUsers() {
        return UserServices.getAllUsers();
    }

    @GetMapping("/users/{id}") //returns a user by its id
    User getUser(@PathVariable Long id) throws UserNotFoundException {
        return UserServices.getUserByID(id);
    }

    @GetMapping("/users/byEmail/{email}") //returns a user by its email
    User getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return UserServices.getUserByEmail(email);
    }

    @PostMapping("/users") //creates a new user in the database
    User createUser(@RequestBody User user) throws UserAlreadyExistsException {
        return UserServices.createUser(user);
    }

    @PutMapping("/users/{id}") //updates a user by its id
    User updateUser(@PathVariable Long id, @RequestBody User user) throws UserNotFoundException {
        return UserServices.updateUserByUserID(id, user);
    }

    @DeleteMapping("/users/{id}") //deletes a user by its id
    User deleteUser(@PathVariable Long id) throws UserNotFoundException, ProfileNotFoundException {
        return UserServices.deleteUser(id);
    }

    // Task paths


    @GetMapping("/tasks") //returns all tasks in the database
    List<TaskWithUserIdsDTO> getAllTasks() {
        return TaskServices.getAllTasks();
    }

    @GetMapping("/tasks/{id}")  //returns a task by its id
    TaskWithUserIdsDTO getTask(@PathVariable Long id) throws TaskNotFoundException {
        return TaskServices.getTaskByTaskID(id);
    }

    @PostMapping("/tasks") //creates a new task in the database
    public TaskWithUserIdsDTO createTask(@RequestBody TaskCreationDTO taskDTO) {
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

    @PutMapping("/tasks/{id}") //updates a task by its id
    TaskWithUserIdsDTO updateTask(@PathVariable Long id, @RequestBody TaskWithUserIdsDTO taskDto) throws TaskNotFoundException {
        Task task = TaskServices.convertDtoToTask(taskDto);
        Task updatedTask = TaskServices.updateTaskByTaskID(id, task);
        return TaskServices.convertToDTO(updatedTask);
    }


    @DeleteMapping("/tasks/{id}") //deletes a task by its id
    Task deleteTask(@PathVariable Long id) throws TaskNotFoundException {
        return TaskServices.deleteTaskByID(id);
    }

    // Profile paths
    @GetMapping("/profiles")   //returns all profiles in the database
    List<Profile> getAllProfiles() {
        return ProfileServices.getAllProfiles();
    }

    @GetMapping("/profiles/{id}") //returns a profile by its id
    Profile getProfile(@PathVariable Long id) throws ProfileNotFoundException {
        return ProfileServices.getProfileByUserID(id);
    }

    @PostMapping("/profiles/{userID}") //creates a new profile in the database
    public Profile createProfile(@PathVariable Long userID, @RequestBody Profile profile) throws PrimaryIdNullOrEmptyException, ProfileNotFoundException, UserNotFoundException, ProfileAlreadyExistsException, IOException {
        return ProfileServices.createAndLinkProfileToUser(userID, profile);
    }

    /* @PostMapping("/profiles/{id}/image")
    public Profile uploadImage@PathVariable Long id, @RequestParam("image") MultipartFile image) throws ProfileNotFoundException, IOException {
       StringBuilder fileName = new StringBuilder();
        Path fileNameAndPath = Paths.get(uploadDirectory, image.getOriginalFilename());
        fileName.append(image.getOriginalFilename());
        Files.write(fileNameAndPath, image.getBytes());
        Profile profile = ProfileServices.getProfileByUserID(id);
        profile.setImage(fileName.toString());
        return ProfileServices.updateProfileByUserID(id, profile);
    }

     */


    @PutMapping("/profiles/{id}") //updates a profile by its id
    Profile updateProfile(@PathVariable Long id, @RequestBody Profile profile) throws ProfileNotFoundException {
        return ProfileServices.updateProfileByUserID(id, profile);
    }

    @DeleteMapping("/profiles/{id}") //deletes a profile by its id
    Profile deleteProfile(@PathVariable Long id) throws ProfileNotFoundException, UserNotFoundException {
        Profile profile = ProfileServices.getProfileByUserID(id);
        UserServices.deleteUser(profile.getUser().getId());
        return ProfileServices.deleteProfileByID(id);
    }


}
