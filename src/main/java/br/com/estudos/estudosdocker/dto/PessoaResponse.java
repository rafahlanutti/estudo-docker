package br.com.estudos.estudosdocker.dto;

import java.time.LocalDate;

import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.validation.SexoValidation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PessoaResponse(String id, String nome, String sobrenome, String sexo, String cpf, LocalDate nascimento, Endereco endereco) {
}
