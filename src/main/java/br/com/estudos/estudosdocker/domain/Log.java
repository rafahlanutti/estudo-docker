package br.com.estudos.estudosdocker.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class Log {

	@Id
	private String Id;
	private String message;


}
