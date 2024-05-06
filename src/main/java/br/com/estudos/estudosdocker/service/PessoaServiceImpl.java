package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.domain.Pessoa;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import br.com.estudos.estudosdocker.mapper.PessoaMapper;
import br.com.estudos.estudosdocker.repository.PessoaRepository;
import org.springframework.stereotype.Service;

@Service
public class PessoaServiceImpl implements PessoaService {

	private final PessoaMapper pessoaMapper;
	private final PessoaRepository repository;

	public PessoaServiceImpl(PessoaMapper pessoaMapper, PessoaRepository repository) {
		this.pessoaMapper = pessoaMapper;
		this.repository = repository;
	}

	@Override
	public PessoaResponse save(PessoaRequest pessoa) {
		final Pessoa saved = pessoaMapper.toPessoa(pessoa);

		repository.save(saved);
		return pessoaMapper.toPessoaResponse(saved);
	}
}
