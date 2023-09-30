package at.codersbay.java.taskapp.rest.controller;

import at.codersbay.java.taskapp.rest.entities.User;
import at.codersbay.java.taskapp.rest.exceptions.*;
import at.codersbay.java.taskapp.rest.services.UserServices;
import at.codersbay.java.taskapp.rest.services.ProfileServices;
import at.codersbay.java.taskapp.rest.services.TaskServices;

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


}
