package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PessoaService {

    PessoaResponse save(final PessoaRequest pessoa);
    PessoaResponse getById(final String id);
    void delete(final String id);
    PessoaResponse update(final String id, final PessoaRequest pessoa);
    Page<PessoaResponse> find(final String nome, final Pageable page);

}
