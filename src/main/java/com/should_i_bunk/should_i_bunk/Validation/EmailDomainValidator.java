package com.should_i_bunk.should_i_bunk.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmailDomainValidator implements ConstraintValidator<NonDisposableEmail, String> {

    private final Set<String> blocked;

    public EmailDomainValidator(@Value("${app.security.jwt.disposable-email}") String domains) {
        this.blocked = Stream.of(domains.split(","))
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext ctx) {
        if (email == null || !email.contains("@")) {
            return true;
        }
        final int atIndex = email.lastIndexOf('@') + 1;
        final int dotIndex = email.lastIndexOf('.');
        final String domain = email.substring(atIndex, dotIndex).toLowerCase();
        return !this.blocked.contains(domain);
    }
}