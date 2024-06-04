package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(final String token);
}
