package tech.devinhouse.devinhortifrutiapi.service.exception;

public class RequiredFieldMissingException extends RuntimeException {
    public RequiredFieldMissingException(String message){
        super(message);
    }
}
