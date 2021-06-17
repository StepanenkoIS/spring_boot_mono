package is.springbootmono.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HandlerRequiredException extends ResponseStatusException {

    public HandlerRequiredException(HttpStatus status, String message, Throwable e) {
        super(status, message, e);
    }

}
