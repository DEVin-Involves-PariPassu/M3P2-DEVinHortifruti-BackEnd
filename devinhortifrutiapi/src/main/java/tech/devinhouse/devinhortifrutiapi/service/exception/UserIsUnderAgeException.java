package tech.devinhouse.devinhortifrutiapi.service.exception;

public class UserIsUnderAgeException extends RuntimeException {
    public UserIsUnderAgeException (String message) {
        super(message);
    }
}
