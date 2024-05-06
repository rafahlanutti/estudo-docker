package br.com.estudos.estudosdocker.controller;

import br.com.estudos.estudosdocker.dto.GetLoveRequest;
import br.com.estudos.estudosdocker.dto.GetLoveResponse;
import br.com.estudos.estudosdocker.service.LoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("love")
public class LoveController {

	private final LoveService loveService;

	@Autowired
	public LoveController(LoveService loveService) {
		this.loveService = loveService;
	}

	@GetMapping("index")
	public GetLoveResponse showLove(GetLoveRequest request) {
		GetLoveResponse response = new GetLoveResponse();
		String message = loveService.showLove(request.getMessage());

		response.setMessage(message);

		return response;
	}
}
