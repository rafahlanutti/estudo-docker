package br.com.estudos.estudosdocker.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidateExceptionHandler {

	//TODO: Adicionar mais exception handlers
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiErrors> handleValidationExceptions(final MethodArgumentNotValidException ex,
		final HttpServletRequest request) {
		final BindingResult bindingResult = ex.getBindingResult();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrors(bindingResult.getAllErrors()));
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiErrors> handleRuntimeException(final RuntimeException ex,
															final HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrors(ex.getMessage()));
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + ex.getMessage());
	}

}
