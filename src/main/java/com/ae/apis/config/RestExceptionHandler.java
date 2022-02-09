package com.ae.apis.config;

import com.ae.apis.config.error.ApiResponseStatus;
import com.ae.apis.config.error.BusinessException;
import com.ae.apis.config.error.NotFoundException;
import com.ae.apis.config.error.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleException(BusinessException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.BUSINESS_LOGIC_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleException(UsernameNotFoundException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.INVALID_AUTHORIZATION, ex.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleException(BadCredentialsException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.INVALID_AUTHORIZATION, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleException(NotFoundException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.RESOURCE_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleException(InternalAuthenticationServiceException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.INVALID_AUTHORIZATION, ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleException(ValidationException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.VALIDATION_EXCEPTION, ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleException(MissingServletRequestParameterException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.MISSING_REQUEST_PARAMETER, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleException(MethodArgumentTypeMismatchException ex) {
        ex.printStackTrace();
        Map<String, String> details = new HashMap<>();
        details.put("paramName", ex.getName());
        details.put("paramValue", ofNullable(ex.getValue()).map(Object::toString).orElse(""));
        details.put("errorMessage", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.METHOD_ARGUMENT_TYPE_MISMATCH, details));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        List<Map<String, String>> details = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, String> detail = new HashMap<>();
                    detail.put("objectName", fieldError.getObjectName());
                    detail.put("field", fieldError.getField());
                    detail.put("rejectedValue", "" + fieldError.getRejectedValue());
                    detail.put("errorMessage", fieldError.getDefaultMessage());
                    details.add(detail);
                });

        return ResponseEntity
                .badRequest()
                .body(ErrorResponse.build(ApiResponseStatus.METHOD_ARGUMENT_VALIDATION_FAILED, details));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.build(ApiResponseStatus.FORBIDDEN, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.build(ApiResponseStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}
