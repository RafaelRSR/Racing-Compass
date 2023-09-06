package rafael.rocha.mscars.domain.car.exceptions;

public class DuplicateCarException extends RuntimeException{
    public DuplicateCarException(String message) {
        super(message);
    }
}
