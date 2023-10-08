package com.ale.blog.handler.exception;

import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.utils.MessageCode;
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

    /**
     * Exception for valid field
     * */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public DataResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return DataResponse.builder()
                .code(MessageCode.ARGUMENT_NOT_VALID)
                .status(DataResponse.ResponseStatus.FAILED)
                .message(errors.toString())
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({AccessDeniedException.class})
    public void handleAccessDeniedException(Exception ex) {
        ex.printStackTrace();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ PropertyReferenceException.class, IllegalArgumentException.class})
    public DataResponse handleRequest(Exception e) {
        e.printStackTrace();
        return DataResponse.builder()
                .code(MessageCode.BAD_QUERY)
                .status(DataResponse.ResponseStatus.FAILED)
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ AppException.class})
    public DataResponse handleAppException(AppException e) {
        e.printStackTrace();
        return e.getResponse();
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
    public DataResponse handleAccessDeniedException(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        return DataResponse.builder()
                .status(DataResponse.ResponseStatus.FAILED)
                .code(MessageCode.DUPLICATE_ENTRY)
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
        errors.put("message", MessageCode.UNKNOWN_ERROR.name());
        return errors;
    }
}
