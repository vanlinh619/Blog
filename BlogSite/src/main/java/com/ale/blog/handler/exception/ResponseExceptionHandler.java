package com.ale.blog.handler.exception;

import com.ale.blog.handler.mapper.response.ResponseData;
import com.ale.blog.handler.utils.MessageType;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionException;

@RestControllerAdvice
public class ResponseExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class})
    public void handleAccessDeniedException(Exception ex) {
        ex.printStackTrace();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ PropertyReferenceException.class, IllegalArgumentException.class})
    public ResponseData handleRequest(Exception e) {
        e.printStackTrace();
        return ResponseData.builder()
                .status(ResponseData.ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ AppException.class})
    public Map<String, String> handleAppException(AppException e) {
        e.printStackTrace();
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessageType().name());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ CompletionException.class})
    public Map<String, String> handleCompletionException(CompletionException e) {
        e.printStackTrace();
        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return errors;
    }

    /**
     * Exception for unique field
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseData handleAccessDeniedException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        return ResponseData.builder()
                .status(ResponseData.ResponseStatus.FAILED)
                .message(MessageType.DUPLICATE_ENTRY.name())
                .build();
    }

    /**
     * Exception for Unknown
    */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ Exception.class })
    public Map<String, String> handleException(Exception e) {
        e.printStackTrace();
        Map<String, String> errors = new HashMap<>();
        errors.put("message", MessageType.UNKNOWN_ERROR.name());
        return errors;
    }
}
