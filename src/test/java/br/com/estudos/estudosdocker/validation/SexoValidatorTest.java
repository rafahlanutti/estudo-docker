package br.com.estudos.estudosdocker.validation;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SexoValidatorTest {

    @Mock
    private SexoValidation sexoValidation;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private SexoValidator sexoValidator;

    private static final String MASCULINO = "M";
    private static final String FEMININO = "F";

    @BeforeEach
    void setup() {
        when(sexoValidation.feminino()).thenReturn(FEMININO);
        when(sexoValidation.masculino()).thenReturn(MASCULINO);
        sexoValidator.initialize(sexoValidation); // Inicializar manualmente o validador
    }

    @Test
    void testIsValidFeminino() {
        assertTrue(sexoValidator.isValid(FEMININO, constraintValidatorContext));
    }

    @Test
    void testIsValidMasculino() {
        assertTrue(sexoValidator.isValid(MASCULINO, constraintValidatorContext));
    }

    @Test
    void testIsValidNullOrEmpty() {
        assertFalse(sexoValidator.isValid(null, constraintValidatorContext));
        assertFalse(sexoValidator.isValid("", constraintValidatorContext));
        assertFalse(sexoValidator.isValid(Strings.EMPTY, constraintValidatorContext));
    }

    @Test
    void testIsValidOtherValues() {
        assertFalse(sexoValidator.isValid("X", constraintValidatorContext));
        assertFalse(sexoValidator.isValid("Masculino", constraintValidatorContext));
        assertFalse(sexoValidator.isValid("Feminino", constraintValidatorContext));
    }
}
