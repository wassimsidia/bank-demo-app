package com.neolynk.demoapp.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.Period;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wassim on 2017/07/16
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DOB.DOBValidator.class)
@Documented
public @interface DOB {

    String message() default "invalid date of birth";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minimumAge();

    int maximumAge() default -1;

    @Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        DOB[] minimumAge();

    }


    class DOBValidator implements ConstraintValidator<DOB, LocalDate> {

        private int minimumAge, maximumAge;

        @Override
        public void initialize(DOB constraintAnnotation) {
            this.minimumAge = constraintAnnotation.minimumAge();
            this.maximumAge = constraintAnnotation.maximumAge();
        }

        @Override
        public boolean isValid(LocalDate dob, ConstraintValidatorContext constraintValidatorContext) {

            if (dob == null) {
                return true;
            }

            Period age = Period.between(dob, LocalDate.now());

            if (age.isNegative()) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("date is in the past").addConstraintViolation();
            }
            if (age.getYears() < minimumAge) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("age is lower than " + minimumAge).addConstraintViolation();
                return false;
            }
            if (maximumAge != -1 && age.getYears() > maximumAge) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate("age is higher than " + maximumAge).addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}