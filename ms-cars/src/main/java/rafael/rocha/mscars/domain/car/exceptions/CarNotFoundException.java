package rafael.rocha.mscars.domain.car.exceptions;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException (String message) {
        super(message);
    }
}
