import br.com.estudos.estudosdocker.validation.SexoValidation;
import br.com.estudos.estudosdocker.validation.SexoValidator;
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
public class SexoValidatorTest {

    @Mock
    private SexoValidation sexoValidation;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @InjectMocks
    private SexoValidator sexoValidator;

    private static final String MASCULINO = "M";
    private static final String FEMININO = "F";

    @BeforeEach
    public void setup() {
        when(sexoValidation.feminino()).thenReturn(FEMININO);
        when(sexoValidation.masculino()).thenReturn(MASCULINO);
        sexoValidator.initialize(sexoValidation); // Inicializar manualmente o validador
    }

    @Test
    public void testIsValidFeminino() {
        assertTrue(sexoValidator.isValid(FEMININO, constraintValidatorContext));
    }

    @Test
    public void testIsValidMasculino() {
        assertTrue(sexoValidator.isValid(MASCULINO, constraintValidatorContext));
    }

    @Test
    public void testIsValidNullOrEmpty() {
        assertFalse(sexoValidator.isValid(null, constraintValidatorContext));
        assertFalse(sexoValidator.isValid("", constraintValidatorContext));
        assertFalse(sexoValidator.isValid(Strings.EMPTY, constraintValidatorContext));
    }

    @Test
    public void testIsValidOtherValues() {
        assertFalse(sexoValidator.isValid("X", constraintValidatorContext));
        assertFalse(sexoValidator.isValid("Masculino", constraintValidatorContext));
        assertFalse(sexoValidator.isValid("Feminino", constraintValidatorContext));
    }
}
