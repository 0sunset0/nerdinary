package nerdinary.hackathon.global.exception;

import static nerdinary.hackathon.global.exception.ErrorCode.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(new ErrorResponse(errorCode.getMessage()));
    }

    //validation 에러
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> bindExceptionHandler(BindingResult bindingResult) {
        HttpStatus status = INVALID_INPUT_VALUE.getStatus();
        StringBuilder reason = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String errorMessage = fieldError.getField() + " : " + fieldError.getDefaultMessage();
            reason.append(errorMessage).append(", ");
        }
        log.warn("ValidationException({}) - {}", status, reason);
        return ResponseEntity.status(status)
            .body(new ErrorResponse(reason.toString()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException ex) {
        HttpStatus status = INVALID_INPUT_VALUE.getStatus();
        String message = ex.getMessage();

        log.warn("{}({}) - {}", ex.getClass().getSimpleName(), status, message);
        return ResponseEntity.status(status)
            .body(new ErrorResponse(message));
    }

    @ExceptionHandler(ServletRequestBindingException.class)// @RequestParam 누락
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleServletRequestBindingException(ServletRequestBindingException ex) {
        log.warn("{} - {}", ex.getClass().getName(), ex.getMessage());
        return ResponseEntity.status(INVALID_INPUT_VALUE.getStatus())
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)// 잘못된 요청 body
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("{} - {}", ex.getClass().getName(), ex.getMessage());
        return ResponseEntity.status(INVALID_INPUT_VALUE.getStatus())
            .body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        // 로그 남기기
        ex.printStackTrace();
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
            .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }
}
