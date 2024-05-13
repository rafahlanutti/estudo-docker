package br.com.estudos.estudosdocker.mapper;

import br.com.estudos.estudosdocker.domain.Pessoa;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
	componentModel = MappingConstants.ComponentModel.SPRING,
	unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PessoaMapper {


	@Mapping(target = "id",
			expression = "java(generateId())")
	Pessoa toPessoa(final PessoaRequest pessoaRequest);

	PessoaResponse toPessoaResponse(final Pessoa pessoaRequest);

	default String generateId() {
		return UUID.randomUUID().toString();
	}
}