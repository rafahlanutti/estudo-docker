package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<UserInfo, Long> {

   UserInfo findByUsername(final String username);
   UserInfo findFirstById(final Long id);

}
