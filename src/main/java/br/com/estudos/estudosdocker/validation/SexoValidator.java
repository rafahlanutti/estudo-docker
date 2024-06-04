package br.com.estudos.estudosdocker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.logging.log4j.util.Strings;

public class SexoValidator implements ConstraintValidator<SexoValidation, String> {
    private String feminino;
    private String masculino;

    @Override
    public void initialize(final SexoValidation constraintAnnotation) {
        this.feminino = constraintAnnotation.feminino();
        this.masculino = constraintAnnotation.masculino();
    }

    @Override
    public boolean isValid(final String sexo, final ConstraintValidatorContext constraintValidatorContext) {
        return Strings.isNotBlank(sexo) && (feminino.equalsIgnoreCase(sexo) || masculino.equalsIgnoreCase(sexo));
    }
}
