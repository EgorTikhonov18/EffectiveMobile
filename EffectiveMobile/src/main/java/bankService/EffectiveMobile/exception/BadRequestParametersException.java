package bankService.EffectiveMobile.exception;

public class BadRequestParametersException extends RuntimeException{
    public BadRequestParametersException(String message) {
        super(message);
    }
}
