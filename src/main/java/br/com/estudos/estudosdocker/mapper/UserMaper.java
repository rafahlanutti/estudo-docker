package br.com.estudos.estudosdocker.mapper;

import br.com.estudos.estudosdocker.domain.UserInfo;
import br.com.estudos.estudosdocker.dto.UserRequest;
import br.com.estudos.estudosdocker.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMaper {

    UserInfo map(UserRequest user);

    UserResponse map(UserInfo user);
}
