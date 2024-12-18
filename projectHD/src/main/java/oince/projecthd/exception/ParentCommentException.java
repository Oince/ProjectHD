package oince.projecthd.exception;

import org.springframework.http.HttpStatus;

public class ParentCommentException extends CommonException{
    public ParentCommentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
