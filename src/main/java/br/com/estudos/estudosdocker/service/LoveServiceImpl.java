package br.com.estudos.estudosdocker.service;

import br.com.estudos.estudosdocker.domain.Log;
import br.com.estudos.estudosdocker.repository.LogRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoveServiceImpl implements LoveService {

	private final LogRepository repository;

	@Autowired
	public LoveServiceImpl(LogRepository repository) {
		this.repository = repository;
	}

	@Override
	public String showLove(String message) {
		if (Strings.isEmpty(message)) {
			return "Hello my love";
		}

		Log log = new Log();
		log.setMessage(message);
		repository.save(log);
		return String.format("%s my love <3", message);
	}
}
