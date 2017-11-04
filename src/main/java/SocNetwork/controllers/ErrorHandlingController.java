package SocNetwork.controllers;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.enums.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Integer> handleEmailExistsException(
            EmailExistsException e){
        return new ResponseEntity<>(ServerResponse.EMAIL_EXISTS.ordinal(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Integer> handleNullPointerException(UserNotFoundException e){
        return new ResponseEntity<>(ServerResponse.USER_NOT_FOUND.ordinal(),
                HttpStatus.NOT_FOUND);
    }

}
