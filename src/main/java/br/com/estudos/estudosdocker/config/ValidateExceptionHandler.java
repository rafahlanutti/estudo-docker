package br.com.estudos.estudosdocker.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ValidateExceptionHandler {

	//TODO: Adicionar mais exception handlers
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrors> handleValidationExceptions(final MethodArgumentNotValidException ex,
		final HttpServletRequest request) {
		final BindingResult bindingResult = ex.getBindingResult();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrors(bindingResult.getAllErrors()));
	}
}
