package SocNetwork.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by aleksei on 11.02.17.
 */
public class EmailExistsException extends Throwable{

    public EmailExistsException(String message) {
        super(message);
    }

}
