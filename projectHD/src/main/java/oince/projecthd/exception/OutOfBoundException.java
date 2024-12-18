package oince.projecthd.exception;

import org.springframework.http.HttpStatus;

public class OutOfBoundException extends CommonException {

    public OutOfBoundException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
