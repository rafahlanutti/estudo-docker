package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import br.com.estudos.estudosdocker.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PessoaService pessoaService;
    private static ObjectMapper mapper;
    private static ObjectWriter ow;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        ow = mapper.writer().withDefaultPrettyPrinter();
    }

    @Test
    void shouldCreateAnewPessoa() throws Exception {
        final var endereco = new Endereco("rua", "bairro", "numero", "estado", "cidade", "cep");
        final var request = new PessoaRequest("John", "Doe", "M", "123456789", LocalDate.now(), endereco);
        final var response = new PessoaResponse("1", "John", "Doe", "M", "123456789", LocalDate.now(), endereco);

        when(pessoaService.save(any(PessoaRequest.class))).thenReturn(response);

        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        final var requestJson = ow.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.nome").value("John"))
                .andExpect(jsonPath("$.sobrenome").value("Doe"))
                .andExpect(jsonPath("$.sexo").value("M"))
                .andExpect(jsonPath("$.cpf").value("123456789"))
                .andExpect(jsonPath("$.endereco").exists());
    }
}