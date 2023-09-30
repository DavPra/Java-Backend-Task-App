package at.codersbay.java.taskapp.rest.controller;

import at.codersbay.java.taskapp.rest.entities.Task;
import at.codersbay.java.taskapp.rest.entities.User;
import at.codersbay.java.taskapp.rest.entities.Profile;
import at.codersbay.java.taskapp.rest.exceptions.*;
import at.codersbay.java.taskapp.rest.services.BusinessServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApplicationController {

    @Autowired
    private BusinessServices businessServices;

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
        return businessServices.getAllUsers();
    }

    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id) throws UserNotFoundException {
        return businessServices.getUserByID(id);
    }

    @GetMapping("/users/byEmail/{email}")
    User getUserByEmail(@PathVariable String email) throws UserNotFoundException {
        return businessServices.getUserByEmail(email);
    }

    @PostMapping("/users")
    User createUser(@RequestBody User user) throws UserAlreadyExistsException {

        HttpStatus status = null;
        String message = "";
        try {
            user = businessServices.createUser(user);
            status = HttpStatus.OK;
            message = "User created";
        } catch (UserAlreadyExistsException UAEE) {
            status = HttpStatus.CONFLICT;
            message = UAEE.getMessage();
        }
        return user;
    }


}
