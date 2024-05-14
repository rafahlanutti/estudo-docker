package br.com.estudos.estudosdocker.dto;

import java.time.LocalDate;
import java.util.Objects;

import br.com.estudos.estudosdocker.domain.Endereco;
import br.com.estudos.estudosdocker.validation.SexoValidation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PessoaRequest(@NotEmpty(message = "O nome não pode ser vazio") String nome,
							@NotEmpty(message = "O sobrenome não pode ser vazio") String sobrenome,
							@NotEmpty(message = "O sexo não pode ser vazio") @SexoValidation String sexo,
							@NotEmpty(message = "O cpf não pode ser vazio") String cpf,
							@NotNull(message = "O nascimento não pode ser vazio") LocalDate nascimento,
							@NotNull(message = "O endereco não pode ser vazio") Endereco endereco) {

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final PessoaRequest that = (PessoaRequest) o;
		return nome.equals(that.nome) &&
				sobrenome.equals(that.sobrenome) &&
				sexo.equals(that.sexo) &&
				cpf.equals(that.cpf) &&
				nascimento.equals(that.nascimento) &&
				endereco.equals(that.endereco);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, sobrenome, sexo, cpf, nascimento, endereco);
	}
}
