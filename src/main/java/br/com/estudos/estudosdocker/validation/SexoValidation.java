package br.com.estudos.estudosdocker.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SexoValidator.class)
public @interface SexoValidation {

	String feminino() default "F";

	String masculino() default "M";

	String message() default "O sexo deve ser informado como 'M' para masculino e 'F' para feminino";

	Class<? extends Payload>[] payload() default {};

	Class<?>[] groups() default {};
}


