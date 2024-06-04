package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.config.PessoaNotFindException;
import br.com.estudos.estudosdocker.domain.Pessoa;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import br.com.estudos.estudosdocker.mapper.PessoaMapper;
import br.com.estudos.estudosdocker.repository.PessoaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaServiceImpl implements PessoaService {

    private final PessoaMapper pessoaMapper;
    private final PessoaRepository repository;

    public PessoaServiceImpl(final PessoaMapper pessoaMapper, final PessoaRepository repository) {
        this.pessoaMapper = pessoaMapper;
        this.repository = repository;
    }

    @Override
    public PessoaResponse save(final PessoaRequest pessoa) {
        final Pessoa saved = pessoaMapper.toPessoa(pessoa);
        repository.save(saved);
        return pessoaMapper.toPessoaResponse(saved);
    }

    @Override
    public PessoaResponse getById(final String id) {
        final var found = repository.findById(id);
        if (found.isPresent()) {
            return pessoaMapper.toPessoaResponse(found.get());
        }

        throw new PessoaNotFindException("Pessoa não encontrada.");
    }

    @Override
    public void delete(final String id) {
        Optional<Pessoa> found = repository.findById(id);
        found.ifPresent(repository::delete);
    }

    @Override
    public PessoaResponse update(final String id, final PessoaRequest pessoa) {
        final var saved = pessoaMapper.toPessoa(pessoa);
        saved.setId(id);

        repository.save(saved);

        return pessoaMapper.toPessoaResponse(saved);
    }

    @Override
    public Page<PessoaResponse> find(final String nome, final Pageable page) {
        final var pessoaByNome = repository.findByNomeContaining(nome, page);
        return pessoaByNome.map(pessoaMapper::toPessoaResponse);
    }
}
