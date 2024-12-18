package oince.projecthd.exception;

import org.springframework.http.HttpStatus;

public class PermissionException extends CommonException{
    public PermissionException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
