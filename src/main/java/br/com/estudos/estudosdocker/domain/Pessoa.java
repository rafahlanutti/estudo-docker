package br.com.estudos.estudosdocker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    private String id;
    private String nome;
    private String sobrenome;
    private String sexo;
    private String cpf;
    private LocalDate nascimento;
    private Endereco endereco;
}
