package br.com.estudos.estudosdocker.config;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import org.springframework.validation.ObjectError;

@Getter
public class ApiErrors {

	private final List<String> errors = new ArrayList<>();

	public ApiErrors(final List<ObjectError> allErrors) {
		allErrors.forEach(error -> this.errors.add(error.getDefaultMessage()));
	}

	public ApiErrors(final String error) {
		this.errors.add(error);
	}
}