package br.com.estudos.estudosdocker.mapper;

import br.com.estudos.estudosdocker.domain.Pessoa;
import br.com.estudos.estudosdocker.dto.PessoaRequest;
import br.com.estudos.estudosdocker.dto.PessoaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
	componentModel = MappingConstants.ComponentModel.SPRING,
	unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PessoaMapper {

	Pessoa toPessoa(PessoaRequest pessoaRequest);

	PessoaResponse toPessoaResponse(Pessoa pessoaRequest);
}