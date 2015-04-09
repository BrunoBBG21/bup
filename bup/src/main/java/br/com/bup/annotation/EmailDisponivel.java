package br.com.bup.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.bup.validator.EmailDisponivelValidator;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EmailDisponivelValidator.class })
@Documented
public @interface EmailDisponivel {
	String message() default "{email.ja.existe}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

