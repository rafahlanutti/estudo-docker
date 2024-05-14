package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends MongoRepository<Pessoa, String> {

    Page<Pessoa> findByNomeContaining(final String nome, final Pageable pageable);

}
