import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.domain.Pessoa;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import br.com.estudos.estudosdocker.mapper.PessoaMapper;
import br.com.estudos.estudosdocker.repository.PessoaRepository;
import br.com.estudos.estudosdocker.service.PessoaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        final var expectedResponse = new PessoaResponse(saved.getId(), saved.getNome(), saved.getSobrenome(),saved.getSexo(), saved.getCpf(), saved.getNascimento(), saved.getEndereco());

        when(pessoaMapper.toPessoa(request)).thenReturn(saved);
        when(pessoaMapper.toPessoaResponse(saved)).thenReturn(expectedResponse);
        when(pessoaRepository.save(saved)).thenReturn(saved);

        final var response = pessoaService.save(request);

        assertEquals(expectedResponse, response);
        verify(pessoaMapper, times(1)).toPessoa(request);
        verify(pessoaRepository, times(1)).save(saved);
        verify(pessoaMapper, times(1)).toPessoaResponse(saved);
    }
}
