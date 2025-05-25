package ru.example.MySocialNetwork.validations.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.example.MySocialNetwork.validations.validators.BirthDateValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
public @interface BirthDate {

    String message() default "{validation.errors.person_birth_date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
