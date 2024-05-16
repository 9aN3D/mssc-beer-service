package guru.springframework.mssc.beer.service.exception;

public class BeerNotFoundException extends RuntimeException {

    public BeerNotFoundException(String message) {
        super(message);
    }

}
