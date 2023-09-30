package at.codersbay.java.taskapp.rest.exceptions;

public class TaskNotFoundException extends Exception{
    public TaskNotFoundException(String message) {
        super(message);
    }
}
