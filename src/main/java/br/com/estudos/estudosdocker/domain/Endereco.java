package br.com.estudos.estudosdocker.domain;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Endereco {
	private final String rua;
	private final String bairro;
	private final String numero;
	private final String estado;
	private final String cidade;
	private final String cep;

}
