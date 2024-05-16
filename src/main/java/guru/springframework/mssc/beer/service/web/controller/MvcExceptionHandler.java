package guru.springframework.mssc.beer.service.web.controller;

import guru.springframework.mssc.beer.service.exception.BeerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException exception) {
        List<String> errors = new ArrayList<>(exception.getConstraintViolations().size());

        exception.getConstraintViolations().forEach(error -> errors.add(error.toString()));

        return new ResponseEntity<>(errors, BAD_REQUEST);
    }

    @ExceptionHandler(BeerNotFoundException.class)
    public ResponseEntity<String> beerNotFoundErrorHandler(BeerNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), NOT_FOUND);
    }

}
