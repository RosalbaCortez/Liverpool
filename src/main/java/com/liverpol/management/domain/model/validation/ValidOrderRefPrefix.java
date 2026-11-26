package com.liverpol.management.domain.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.util.regex.Pattern;

@Documented
@Constraint(validatedBy = OrderRefPrefixValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrderRefPrefix {

    String message() default "El filtro de fecha/referencia debe estar vacío o cumplir con: 1 dígito, AAAA, AAAA-M(M) o AAAA-M(M)-D(D)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class OrderRefPrefixValidator implements ConstraintValidator<ValidOrderRefPrefix, String> {

    private static final Pattern ALLOWED_PREFIX = Pattern.compile(
            "^(?:\\d|\\d{4}(?:-(?:\\d{1,2}(?:-\\d{1,2})?)?)?)?$"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true;
        }
        return ALLOWED_PREFIX.matcher(value.trim()).matches();
    }
}
