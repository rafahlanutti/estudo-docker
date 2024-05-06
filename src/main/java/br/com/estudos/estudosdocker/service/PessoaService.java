package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;

public interface PessoaService {

	public PessoaResponse save(final PessoaRequest pessoa);
}
