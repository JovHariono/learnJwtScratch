package com.alibu2.spring_security_asymetric_encryption2.handler;

import com.alibu2.spring_security_asymetric_encryption2.exception.BusinessException;
import com.alibu2.spring_security_asymetric_encryption2.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(final BusinessException ex) {

        final ErrorResponse body = ErrorResponse.builder()
                .code(ex.getErrorCode().getCode())
                .message(ex.getMessage())
                .build();

        log.info("Business Exception: {}", ex.getMessage());
        log.debug(ex.getMessage(), ex);

        return ResponseEntity.status(ex.getErrorCode()
                        .getStatus() != null ? ex.getErrorCode()
                        .getStatus() : HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleException(final DisabledException ex) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.ERR_USER_DISABLED.getCode())
                .message(ErrorCode.ERR_USER_DISABLED.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.ERR_USER_DISABLED.getStatus())
                .body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleException(final BadCredentialsException exp) {
        log.debug(exp.getMessage(), exp);

        final ErrorResponse response = ErrorResponse.builder()
                .message(ErrorCode.BAD_CREDENTIALS.getDefaultMessage())
                .code(ErrorCode.BAD_CREDENTIALS.getCode())
                .build();

        return ResponseEntity.status(ErrorCode.BAD_CREDENTIALS.getStatus())
                .body(response);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UsernameNotFoundException exp) {
        log.debug(exp.getMessage(), exp);

        final ErrorResponse response = ErrorResponse.builder()
                .message(ErrorCode.USERNAME_NOT_FOUND.getDefaultMessage())
                .code(ErrorCode.USERNAME_NOT_FOUND.getCode())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(EntityNotFoundException exp) {
        log.debug(exp.getMessage(), exp);

        final ErrorResponse response = ErrorResponse.builder()
                .code("TBD")
                .message(exp.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException exp) {
        final List<ErrorResponse.ValidationError> errors = new ArrayList<>();
        exp.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    final String fieldName = ((FieldError) error).getField();
                    final String errorCode = error.getDefaultMessage();
                    errors.add((ErrorResponse.ValidationError.builder()
                            .field(fieldName)
                            .code(errorCode)
                            .message(errorCode)
                            .build()));
                });

        final ErrorResponse errorResponse = ErrorResponse.builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exp) {
        log.error(exp.getMessage(), exp);

        final ErrorResponse response = ErrorResponse.builder()
                .code(ErrorCode.INTERNAL_EXCEPTION.getCode())
                .message(ErrorCode.INTERNAL_EXCEPTION.getDefaultMessage())
                .build();

        return ResponseEntity.status(ErrorCode.INTERNAL_EXCEPTION.getStatus())
                .body(response);
    }
}
