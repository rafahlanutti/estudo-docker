package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import br.com.estudos.estudosdocker.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @PostMapping
    ResponseEntity<PessoaResponse> salvarPessoa(@Valid @RequestBody final PessoaRequest pessoa) {
        return ResponseEntity.ok(service.save(pessoa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> get(@PathVariable final String id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaResponse> update(@PathVariable final String id, @Valid @RequestBody final PessoaRequest dto) {
        return ResponseEntity.ok(service.update(id, dto));

    }

    @GetMapping
    public Page<PessoaResponse> find(final String nome, final Pageable pageRequest) {
        Page<PessoaResponse> result = service.find(nome, pageRequest);
        return new PageImpl<>(result.getContent(), pageRequest, result.getTotalElements());
    }

}
