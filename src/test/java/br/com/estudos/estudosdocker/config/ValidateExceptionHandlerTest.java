package br.com.estudos.estudosdocker.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateExceptionHandlerTest {

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private ValidateExceptionHandler validateExceptionHandler;

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void returnsBadRequestAndApiErrors() {
        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Collections.emptyList());

        final var responseEntity = validateExceptionHandler.handleValidationExceptions(methodArgumentNotValidException, httpServletRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void returnsBadRequestAndApiErrorsWithObjectErrors() {
        final var objectErrors = new ArrayList<ObjectError>();

        objectErrors.add(new FieldError("pessoaRequest", "nome", "O nome não pode ser vazio"));
        objectErrors.add(new FieldError("pessoaRequest", "sobrenome", "O sobrenome não pode ser vazio"));
        objectErrors.add(new FieldError("pessoaRequest", "sexo", "O sexo não pode ser vazio"));
        objectErrors.add(new FieldError("pessoaRequest", "cpf", "O cpf não pode ser vazio"));
        objectErrors.add(new FieldError("pessoaRequest", "nascimento", "O nascimento não pode ser vazio"));
        objectErrors.add(new FieldError("pessoaRequest", "endereco", "O endereco não pode ser vazio"));

        when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(objectErrors);


        final var responseEntity = validateExceptionHandler.handleValidationExceptions(methodArgumentNotValidException, httpServletRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getErrors());
        assertEquals(6, responseEntity.getBody().getErrors().size());
    }
}
