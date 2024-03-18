package com.winterhold.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;

public class AfterValidator implements ConstraintValidator<After, Object> {

    private String previousDateField;
    private String subsequentDateField;

    @Override
    public void initialize(After constraintAnnotation) {
        this.previousDateField = constraintAnnotation.previousDateField();
        this.subsequentDateField = constraintAnnotation.subsequentDateField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDate previousDateValue = (LocalDate)(new BeanWrapperImpl(value).getPropertyValue(previousDateField));
        LocalDate subsequentDateValue = (LocalDate)(new BeanWrapperImpl(value).getPropertyValue(subsequentDateField));
        if(previousDateValue == null || subsequentDateValue == null) {
            return true;
        }
        return previousDateValue.isBefore(subsequentDateValue);
    }
}
