package com.should_i_bunk.should_i_bunk.exceptionHandling;

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

import static com.should_i_bunk.should_i_bunk.exceptionHandling.ErrorCode.ERR_USER_DISABLED;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ApplicationExceptionHandler {

@ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleException(final BusinessException e) {

    final ErrorResponse body =  ErrorResponse.builder()
            .code(e.getErrorCode().getCode())
            .message(e.getMessage())
            .build();
    log.info("Business exception: {}", e.getMessage());
    log.debug(e.getMessage() , e);

    return ResponseEntity.status(e.getErrorCode().getStatus() != null ? e.getErrorCode().getStatus() :
                    HttpStatus.BAD_REQUEST
            ).body(body);
}

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(final DisabledException e) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ERR_USER_DISABLED.getCode())
                .message(ERR_USER_DISABLED.getDefaultMessage())
                .build();
        log.info("User disabled exception: {}", e.getMessage());
        log.debug(e.getMessage(), e);
        return ResponseEntity.status(ERR_USER_DISABLED.getStatus()).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBAdCredentialException(final BadCredentialsException e) {
    final ErrorResponse body = ErrorResponse.builder()
            .code(ErrorCode.BAD_CREDENTIALS.getCode())
            .message(ErrorCode.BAD_CREDENTIALS.getDefaultMessage())
            .build();
    log.info("Bad credentials exception: {}", e.getMessage());
    log.debug(e.getMessage(), e);
    return ResponseEntity.status(ErrorCode.BAD_CREDENTIALS.getStatus()).body(body);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(final UsernameNotFoundException e) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.USERNAME_NOT_FOUND.getCode())
                .message(ErrorCode.USERNAME_NOT_FOUND.getDefaultMessage())
                .build();
        log.info("Username not found exception: {}", e.getMessage());
        log.debug(e.getMessage(), e);
        return ResponseEntity.status(ErrorCode.USERNAME_NOT_FOUND.getStatus()).body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException e) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.USER_NOT_FOUND.getCode())
                .message(ErrorCode.USER_NOT_FOUND.getDefaultMessage())
                .build();
        log.info("Entity not found exception: {}", e.getMessage());
        log.debug(e.getMessage(), e);
        return ResponseEntity.status(ErrorCode.USER_NOT_FOUND.getStatus()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        final List<ErrorResponse.ValidationError> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
           final String fieldName = ((FieldError) error).getField();
           final String errorCode = error.getDefaultMessage();
           errors.add(ErrorResponse.ValidationError.builder()
                           .field(fieldName)
                           .code(errorCode)
                           .build());
        });
        final ErrorResponse errorResponse = ErrorResponse.builder()
                                                            .validationErrors(errors)
                                                            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e) {
        final ErrorResponse body = ErrorResponse.builder()
                .code(ErrorCode.INTERNAL_EXCEPTION.getCode())
                .message(ErrorCode.INTERNAL_EXCEPTION.getDefaultMessage())
                .build();
        log.error("Internal exception: {}", e.getMessage());
        log.debug(e.getMessage(), e);
        return ResponseEntity.status(ErrorCode.INTERNAL_EXCEPTION.getStatus()).body(body);
    }



}
