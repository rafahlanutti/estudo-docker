package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.domain.Pessoa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest()
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    void testSaved() {
        // Given
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var pessoa = new Pessoa("1", "John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        // When
        final var saved = pessoaRepository.save(pessoa);

        // Then
        assertNotNull(saved);
        assertEquals(pessoa, saved);
    }
    @Test
    void testFindAll() {
        // Given
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var pessoa = new Pessoa("1", "John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        pessoaRepository.save(pessoa);

        // When
        final var pessoas = pessoaRepository.findAll();

        // Then
        assertEquals(1, pessoas.size());
        assertEquals("John", pessoas.get(0).getNome());
    }

}