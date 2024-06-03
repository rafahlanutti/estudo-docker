package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.config.PessoaNotFindException;
import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.domain.Pessoa;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import br.com.estudos.estudosdocker.mapper.PessoaMapper;
import br.com.estudos.estudosdocker.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceImplTest {

    @Mock
    private PessoaMapper pessoaMapper;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaServiceImpl pessoaService;


    @Test
    void testSave() {
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var request = new PessoaRequest("John", "Doe", "M", "123456789", LocalDate.now(), endereco);
        final var saved = new Pessoa("1", "John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        final var expectedResponse = new PessoaResponse(saved.getId(), saved.getNome(), saved.getSobrenome(), saved.getSexo(), saved.getCpf(), saved.getNascimento(), saved.getEndereco());

        when(pessoaMapper.toPessoa(request)).thenReturn(saved);
        when(pessoaMapper.toPessoaResponse(saved)).thenReturn(expectedResponse);
        when(pessoaRepository.save(saved)).thenReturn(saved);

        final var response = pessoaService.save(request);

        assertEquals(expectedResponse, response);
        verify(pessoaMapper, times(1)).toPessoa(request);
        verify(pessoaRepository, times(1)).save(saved);
        verify(pessoaMapper, times(1)).toPessoaResponse(saved);
    }

    @Test
    void testGetById_PessoaNotFound() {
        final var id = "1";
        when(pessoaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PessoaNotFindException.class, () -> pessoaService.getById(id));
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    void testDelete() {
        final var id = "1";
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var pessoa = new Pessoa(id, "John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        when(pessoaRepository.findById(id)).thenReturn(Optional.of(pessoa));

        pessoaService.delete(id);

        verify(pessoaRepository, times(1)).findById(id);
        verify(pessoaRepository, times(1)).delete(pessoa);
    }

    @Test
    void testUpdate() {
        final var id = "1";
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var request = new PessoaRequest("John", "Doe", "M", "123456789", LocalDate.now(), endereco);
        final var updated = new Pessoa(id, "John", "Doe", "M", "123456789", LocalDate.now(), endereco);
        final var expectedResponse = new PessoaResponse(updated.getId(), updated.getNome(), updated.getSobrenome(), updated.getSexo(), updated.getCpf(), updated.getNascimento(), updated.getEndereco());

        when(pessoaMapper.toPessoa(request)).thenReturn(updated);
        when(pessoaRepository.save(updated)).thenReturn(updated);
        when(pessoaMapper.toPessoaResponse(updated)).thenReturn(expectedResponse);

        final var response = pessoaService.update(id, request);

        assertEquals(expectedResponse, response);
        verify(pessoaMapper, times(1)).toPessoa(request);
        verify(pessoaRepository, times(1)).save(updated);
        verify(pessoaMapper, times(1)).toPessoaResponse(updated);
    }

    @Test
    void testFind() {
        final var nome = "John";
        final var pageable = Pageable.unpaged();
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var pessoa = new Pessoa("1", "John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        final var page = new PageImpl<>(Collections.singletonList(pessoa));
        PessoaResponse pessoaResponse = new PessoaResponse(pessoa.getId(), pessoa.getNome(), pessoa.getSobrenome(), pessoa.getSexo(), pessoa.getCpf(), pessoa.getNascimento(), pessoa.getEndereco());
        final var expectedResponse = new PageImpl<>(Collections.singletonList(pessoaResponse));

        when(pessoaRepository.findByNomeContaining(nome, pageable)).thenReturn(page);
        when(pessoaMapper.toPessoaResponse(pessoa)).thenReturn(pessoaResponse);

        final var response = pessoaService.find(nome, pageable);

        assertEquals(expectedResponse, response);
        verify(pessoaRepository, times(1)).findByNomeContaining(nome, pageable);
        verify(pessoaMapper, times(1)).toPessoaResponse(pessoa);
    }
}
