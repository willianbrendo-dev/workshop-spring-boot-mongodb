package com.willianbrendo.workshopmongo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.willianbrendo.workshopmongo.domain.User;
import com.willianbrendo.workshopmongo.services.UserService;

@RestController // 🎯 Anotação que combina @Controller e @ResponseBody. Indica que a classe é um Controller REST.
@RequestMapping(value = "/users") // 🎯 Anotação que define o caminho base (endpoint) para todos os métodos desta classe: http://localhost:8080/users
public class UserResource {

	@Autowired // Injeção de dependência da camada de Serviço
	private UserService service;
	
	/**
	 * Endpoint para buscar todos os usuários.
	 * Mapeado para requisições GET em /users.
	 * * @return ResponseEntity<List<User>>: Uma lista de usuários com status HTTP 200 OK.
	 */
	@GetMapping // 🎯 Anotação que mapeia este método para o método HTTP GET no caminho base (/users)
	public ResponseEntity<List<User>> findAll() {
		
		// 1. Chama o método findAll() na camada de Serviço (que acessa o Repositório/MongoDB)
		List<User> list = service.findAll();
		
		// 2. Constrói a resposta HTTP:
		// - ResponseEntity.ok(): Retorna a resposta com o status 200 OK.
		// - .body(list): Define o corpo da resposta como a lista de usuários (que será convertida para JSON).
		return ResponseEntity.ok().body(list);
	}
}
