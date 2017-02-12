package SocNetwork.controllers;

import SocNetwork.exceptions.EmailExistsException;
import SocNetwork.models.enums.ServerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by aleksei on 12.02.17.
 */
@ControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ServerResponse> handleEmailExistsException(
            EmailExistsException e){
        return new ResponseEntity<ServerResponse>(ServerResponse.EMAIL_EXISTS,
                HttpStatus.BAD_REQUEST);
    }

}
