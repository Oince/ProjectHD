package oince.projecthd.exception;

import org.springframework.http.HttpStatus;

public class DuplicateMemberException extends CommonException{
    public DuplicateMemberException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
