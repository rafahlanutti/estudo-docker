package br.com.estudos.estudosdocker.mapper;

import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.domain.Pessoa;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaMapperTest {

    private final PessoaMapper mapper = Mappers.getMapper(PessoaMapper.class);

    @Test
    void testToPessoa() {
        // Given
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var request = new PessoaRequest("John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        // When
        final var pessoa = mapper.toPessoa(request);

        // Then
        assertEquals("John", pessoa.getNome());
        assertEquals("Doe", pessoa.getSobrenome());
        assertEquals("M", pessoa.getSexo());
        assertEquals("123456789", pessoa.getCpf());
    }

    @Test
    void testToPessoaResponse() {
        // Given
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var pessoa = new Pessoa("1", "John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        // When
        final var response = mapper.toPessoaResponse(pessoa);

        // Then
        assertEquals("1", response.id());
        assertEquals("John", response.nome());
        assertEquals("Doe", response.sobrenome());
        assertEquals("M", response.sexo());
        assertEquals("123456789", response.cpf());
    }
}
