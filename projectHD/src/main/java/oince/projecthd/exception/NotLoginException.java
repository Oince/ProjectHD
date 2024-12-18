package oince.projecthd.exception;

import org.springframework.http.HttpStatus;

public class NotLoginException extends CommonException{
    public NotLoginException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
