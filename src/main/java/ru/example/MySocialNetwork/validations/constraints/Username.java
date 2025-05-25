package ru.example.MySocialNetwork.validations.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotNull(message = "{validation.errors.person_username}")
@Size(min=2, max=40, message = "{validation.errors.person_username}")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Username {

    String message() default "{validation.errors.person_username}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}