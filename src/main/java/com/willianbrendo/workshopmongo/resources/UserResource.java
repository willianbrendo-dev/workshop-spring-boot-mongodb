package com.willianbrendo.workshopmongo.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.willianbrendo.workshopmongo.domain.User;
import com.willianbrendo.workshopmongo.dto.UserDTO;
import com.willianbrendo.workshopmongo.services.UserService;

@RestController // 🎯 Anotação que combina @Controller e @ResponseBody. Indica que a classe é um
				// Controller REST.
@RequestMapping(value = "/users") // 🎯 Anotação que define o caminho base (endpoint) para todos os métodos desta
									// classe: http://localhost:8080/users
public class UserResource {

	@Autowired // Injeção de dependência da camada de Serviço
	private UserService service;

	/**
	 * Endpoint para buscar todos os usuários. Mapeado para requisições GET em
	 * /users. * @return ResponseEntity<List<User>>: Uma lista de usuários com
	 * status HTTP 200 OK.
	 */
	@GetMapping // 🎯 Anotação que mapeia este método para o método HTTP GET no caminho base
				// (/users)
	public ResponseEntity<List<UserDTO>> findAll() {

		// 1. Chama o método findAll() na camada de Serviço (que acessa o
		// Repositório/MongoDB)
		List<User> list = service.findAll();

		// 2. 🎯 Converte a lista de User (Entidade) para List de UserDTO
		// - list.stream(): Cria um stream (fluxo de dados).
		// - .map(x -> new UserDTO(x)): Mapeia cada User 'x' para um novo UserDTO.
		// - .collect(Collectors.toList()): Coleta o resultado de volta em uma List.
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());

		// 2. Constrói a resposta HTTP:
		// - ResponseEntity.ok(): Retorna a resposta com o status 200 OK.
		// - .body(list): Define o corpo da resposta como a lista de usuários (que será
		// convertida para JSON).
		return ResponseEntity.ok().body(listDto);
	}
	
	/**
     * Endpoint para buscar um usuário por ID.
     * Mapeado para requisições GET em /users/{id}.
     * @param id O ID (String) passado na URL.
     * @return ResponseEntity<UserDTO> com status 200 OK.
     */
    @GetMapping(value = "/{id}") // 🎯 Mapeia para um GET com uma variável 'id' na URL
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        
        // 1. Chama o método findById() na camada de Serviço.
        // Retorna a Entidade User ou lança a exceção 404.
        User obj = service.findById(id);
        
        // 2. Converte a Entidade User para o DTO.
        UserDTO objDto = new UserDTO(obj);
        
        // 3. Retorna a resposta com o DTO e status 200 OK.
        return ResponseEntity.ok().body(objDto);
    }
    
    
    /**
     * Endpoint para inserir um novo usuário.
     * Mapeado para requisições POST em /users.
     * @param objDto O DTO com os dados do novo usuário, recebido no corpo da requisição (JSON).
     * @return ResponseEntity<Void> com status 201 Created e o cabeçalho 'Location'.
     */
    @PostMapping // 🎯 Mapeia para requisições HTTP POST no caminho base (/users)
    public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
        
        // 1. Converte o DTO (recebido do cliente) para a Entidade User (que será salva)
        User obj = fromDTO(objDto);
        
        // 2. Chama o Service para salvar a entidade no banco. O objeto retornado agora tem o ID.
        obj = service.insert(obj);
        
        // 3. Constrói a URI do novo recurso criado (Ex: /users/65b0586e3f1979b007068d17)
        // Isso é uma boa prática REST: retornar o endereço de onde o novo recurso pode ser acessado.
        java.net.URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()     // Pega a URI atual (http://localhost:8080/users)
                .path("/{id}")            // Adiciona o caminho com o ID
                .buildAndExpand(obj.getId()) // Coloca o ID gerado no caminho
                .toUri();                 // Converte para o objeto URI
        
        // 4. Retorna a resposta:
        // - Status 201 (Created)
        // - Cabeçalho Location: A URI do novo recurso. O corpo (body) é vazio (Void).
        return ResponseEntity.created(uri).build();
    }

    /**
     * Método auxiliar para converter UserDTO para a Entidade User.
     * @param objDto O DTO a ser convertido.
     * @return A entidade User pronta para persistência.
     */
    public User fromDTO(UserDTO objDto) {
        // Criamos uma nova Entidade, usando o ID do DTO (que pode ser nulo no INSERT)
        // e os demais dados. A senha e telefone são setados como nulos aqui, mas você
        // pode ajustar o DTO para incluir o mínimo necessário para a criação.
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
    }
    
    
    /**
     * Endpoint para deletar um usuário por ID.
     * Mapeado para requisições DELETE em /users/{id}.
     * @param id O ID (String) passado na URL.
     * @return ResponseEntity<Void> com status 204 No Content (sucesso sem corpo).
     */
    @DeleteMapping(value = "/{id}") // 🎯 Mapeia para DELETE com a variável 'id' na URL
    public ResponseEntity<Void> delete(@PathVariable String id) {
        
        // 1. Chama o método delete() na camada de Serviço.
        // O tratamento 404 já está embutido na chamada do serviço.
        service.delete(id);
        
        // 2. Retorna a resposta com o status 204 No Content. 
        // O .build() cria a resposta sem corpo.
        return ResponseEntity.noContent().build();
    }
    
    
    /**
     * Endpoint para atualizar um usuário existente.
     * Mapeado para requisições PUT em /users/{id}.
     * @param id O ID (String) do usuário a ser atualizado.
     * @param objDto O DTO com os novos dados no corpo da requisição (JSON).
     * @return ResponseEntity<Void> com status 204 No Content (sucesso sem corpo).
     */
    @PutMapping(value = "/{id}") // 🎯 Mapeia para PUT com a variável 'id' na URL
    public ResponseEntity<Void> update(@RequestBody UserDTO objDto, @PathVariable String id) {
        
        // 1. Converte o DTO recebido para a Entidade User
        User obj = fromDTO(objDto);
        
        // 2. Garante que a Entidade tenha o ID correto (vindo da URL)
        obj.setId(id);
        
        // 3. Chama o método update do Service para buscar, atualizar e salvar
        service.update(id, obj);
        
        // 4. Retorna o status 204 No Content para indicar sucesso sem corpo.
        // Se você quisesse retornar o objeto atualizado (prática aceitável),
        // o retorno seria ResponseEntity<UserDTO> e o status 200 OK.
        return ResponseEntity.noContent().build();
    }

}
