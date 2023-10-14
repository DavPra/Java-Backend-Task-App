package at.codersbay.java.taskapp.rest.exceptions;

public class ProfileAlreadyExistsException extends Exception{
    public ProfileAlreadyExistsException(String message) {
        super(message);
    }
}
