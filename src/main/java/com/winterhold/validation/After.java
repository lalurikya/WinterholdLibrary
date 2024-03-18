package com.winterhold.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = AfterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface After {

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
    public String message();
    public String previousDateField();
    public String subsequentDateField();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        After[] value();
    }
}
