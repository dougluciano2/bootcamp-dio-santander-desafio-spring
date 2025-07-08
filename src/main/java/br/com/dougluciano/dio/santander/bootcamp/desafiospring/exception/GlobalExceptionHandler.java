package br.com.dougluciano.dio.santander.bootcamp.desafiospring.exception;

import br.com.dougluciano.dio.santander.bootcamp.desafiospring.dto.ApiErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiErrorDto> handlerNoSuchException(NoSuchElementException e, HttpServletRequest request){
        var httpStatus = HttpStatus.NOT_FOUND;
        var apiError = new ApiErrorDto(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "Resource not found.",
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError,httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDto> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request){
        var httpStatus = HttpStatus.BAD_REQUEST;
        var apiError = new ApiErrorDto(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError,httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorDto> handleAAccessDeniedException(AccessDeniedException e, HttpServletRequest request){
        var httpStatus = HttpStatus.FORBIDDEN;
        var apiError = new ApiErrorDto(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                "Access denied. You have no permission to access this resource, contact the adminstrator.",
                request.getRequestURI()
        );
        return new ResponseEntity<>(apiError,httpStatus);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiErrorDto> handleUnexpectedException(Throwable e, HttpServletRequest request){
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var apiError = new ApiErrorDto(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );

        logger.error("Unexpected error occured at path: " + request.getRequestURI(), e);
        return new ResponseEntity<>(apiError,httpStatus);
    }
}
