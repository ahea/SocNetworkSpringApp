package SocNetwork.controllers;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.exceptions.UserNotFoundException;
import SocNetwork.models.enums.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorHandlingController {

    private final Logger logger = LoggerFactory.getLogger(ErrorHandlingController.class);

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<Integer> handleEmailExistsException(
            EmailExistsException e){

        logger.info(e.toString());
        return new ResponseEntity<>(ServerResponse.EMAIL_EXISTS.ordinal(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Integer> handleNullPointerException(UserNotFoundException e){

        logger.info(e.toString());
        return new ResponseEntity<>(ServerResponse.USER_NOT_FOUND.ordinal(),
                HttpStatus.NOT_FOUND);
    }

}
