package oince.projecthd.exception;

import org.springframework.http.HttpStatus;

public class AlreadyThumbupException extends CommonException{
    public AlreadyThumbupException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
