package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.Log;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String> {

}
