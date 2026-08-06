package s01_exception.t02_exceptionGenimi;

public class InsufficientStuckException extends RuntimeException {

    public InsufficientStuckException(String message) {
        super(message);
    }
}
