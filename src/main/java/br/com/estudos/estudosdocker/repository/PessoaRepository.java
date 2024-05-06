package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends MongoRepository<Pessoa, String> {

}
