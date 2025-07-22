package com.should_i_bunk.should_i_bunk.exceptionHandling;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
public enum ErrorCode {
USER_NOT_FOUND("USER_NOT_FOUND", "User not found with id %s", NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("CHANGE_PASSWORD_MISMATCH","new password and confirm new password are not same" ,BAD_REQUEST ),
    INVALID_OLD_PASSWORD("INVALID_OLD_PASSWORD","Old password is wrong" , BAD_REQUEST ),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED","account already deactivated" , BAD_REQUEST);


    private final String code;
    private final String defaultMessage;
    private final HttpStatus Status;

    ErrorCode(String code, String defaultMessage, HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        Status = status;
    }
}
