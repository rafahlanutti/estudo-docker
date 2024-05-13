package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import br.com.estudos.estudosdocker.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PessoaController {

	private final PessoaService service;

	@Autowired
	public PessoaController(final PessoaService service) {
		this.service = service;
	}

	@PostMapping("/users")
	ResponseEntity<PessoaResponse> salvarPessoa(@Valid @RequestBody final PessoaRequest pessoa) {
		return ResponseEntity.ok(service.save(pessoa));
	}
}
