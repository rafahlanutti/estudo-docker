package br.com.estudos.estudosdocker.domain;

import java.time.LocalDate;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Pessoa {

	@Id
	private final String id;
	private final String nome;
	private final String sobrenome;
	private final String sexo;
	private final String cpf;
	private final LocalDate nascimento;
	private final Endereco endereco;
}
