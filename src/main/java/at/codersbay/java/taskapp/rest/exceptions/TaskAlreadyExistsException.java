package at.codersbay.java.taskapp.rest.exceptions;

public class TaskAlreadyExistsException extends Exception{
    public TaskAlreadyExistsException(String message) {
        super(message);
    }
}
