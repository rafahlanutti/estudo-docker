package br.com.estudos.estudosdocker.repository;

import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.domain.Pessoa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
class PessoaRepositoryTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setup() {
        pessoaRepository.deleteAll();
    }

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

    @Test
    void testFindByNomeContaining() {
        // Given
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var pessoa1 = new Pessoa("1", "John", "Doe", "M", "123456789", LocalDate.now(), endereco);
        final var pessoa2 = new Pessoa("2", "Jane", "Doe", "F", "987654321", LocalDate.now(), endereco);

        pessoaRepository.save(pessoa1);
        pessoaRepository.save(pessoa2);

        // When
        final var pessoas = pessoaRepository.findByNomeContaining("John", Pageable.ofSize(1));

        // Then
        assertEquals(1, pessoas.getTotalElements());
        assertEquals("John", pessoas.getContent().get(0).getNome());
    }

}