package br.com.estudos.estudosdocker.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Endereco {
	private final String rua;
	private final String bairro;
	private final String numero;
	private final String estado;
	private final String cidade;
	private final String cep;

}
