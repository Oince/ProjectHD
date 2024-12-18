package oince.projecthd.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> commonEx(CommonException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResult(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> httpMessageNotReadableEx(HttpMessageNotReadableException e) {
        log.warn("JSON parsing error.");
        ErrorResult errorResult = new ErrorResult("요청 데이터 형식이 잘못되었습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> methodArgumentNotValidEx(MethodArgumentNotValidException e) {
        log.warn("Request data validation fail.");
        ErrorResult errorResult = new ErrorResult(e.getBindingResult().getAllErrors().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> serverEx(RuntimeException e) {
        ErrorResult errorResult = new ErrorResult("서버 오류");
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResult);
    }
}
