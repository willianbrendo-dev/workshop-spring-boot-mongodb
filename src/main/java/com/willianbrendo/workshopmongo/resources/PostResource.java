package com.willianbrendo.workshopmongo.resources;

import java.util.List;

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

import com.willianbrendo.workshopmongo.domain.Post;
import com.willianbrendo.workshopmongo.services.PostService;

@RestController // 🎯 Anotação que combina @Controller e @ResponseBody. Indica que a classe é um
				// Controller REST.
@RequestMapping(value = "/posts") 
public class PostResource {

	@Autowired // Injeção de dependência da camada de Serviço
	private PostService service;

	/**
	 * Endpoint para buscar todos os post. Mapeado para requisições GET em
	 * /posts. * @return ResponseEntity<List<Post>>: Uma lista de post com
	 * status HTTP 200 OK.
	 */
	@GetMapping // 🎯 Anotação que mapeia este método para o método HTTP GET no caminho base
				// (/users)
	public ResponseEntity<List<Post>> findAll() {

		// 1. Chama o método findAll() na camada de Serviço (que acessa o
		// Repositório/MongoDB)
		List<Post> list = service.findAll();

		// 2. Constrói a resposta HTTP:
		// - ResponseEntity.ok(): Retorna a resposta com o status 200 OK.
		// - .body(list): Define o corpo da resposta como a lista de usuários (que será
		// convertida para JSON).
		return ResponseEntity.ok().body(list);
	}
	
	/**
     * Endpoint para buscar um post por ID.
     * Mapeado para requisições GET em /posts/{id}.
     * @param id O ID (String) passado na URL.
     * @return ResponseEntity<Post> com status 200 OK.
     */
    @GetMapping(value = "/{id}") // 🎯 Mapeia para um GET com uma variável 'id' na URL
    public ResponseEntity<Post> findById(@PathVariable String id) {
        
        // 1. Chama o método findById() na camada de Serviço.
        // Retorna a Entidade User ou lança a exceção 404.
        Post obj = service.findById(id);
        
        // 3. Retorna a resposta com o DTO e status 200 OK.
        return ResponseEntity.ok().body(obj);
    }
    
    
    /**
     * Endpoint para inserir um novo post.
     * Mapeado para requisições POST em /posts.
     * @return ResponseEntity<Void> com status 201 Created e o cabeçalho 'Location'.
     */
    @PostMapping // 🎯 Mapeia para requisições HTTP POST no caminho base (/posts)
    public ResponseEntity<Void> insert(@RequestBody Post obj) {
        
        // 2. Chama o Service para salvar a entidade no banco. O objeto retornado agora tem o ID.
        obj = service.insert(obj);
        
        // 3. Constrói a URI do novo recurso criado (Ex: /posts/65b0586e3f1979b007068d17)
        // Isso é uma boa prática REST: retornar o endereço de onde o novo recurso pode ser acessado.
        java.net.URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()     // Pega a URI atual (http://localhost:8080/posts)
                .path("/{id}")            // Adiciona o caminho com o ID
                .buildAndExpand(obj.getId()) // Coloca o ID gerado no caminho
                .toUri();                 // Converte para o objeto URI
        
        // 4. Retorna a resposta:
        // - Status 201 (Created)
        // - Cabeçalho Location: A URI do novo recurso. O corpo (body) é vazio (Void).
        return ResponseEntity.created(uri).build();
    }   
    
    /**
     * Endpoint para deletar um usuário por ID.
     * Mapeado para requisições DELETE em /posts/{id}.
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
     * Mapeado para requisições PUT em /posts/{id}.
     * @param id O ID (String) do post a ser atualizado.
     * @return ResponseEntity<Void> com status 204 No Content (sucesso sem corpo).
     */
    @PutMapping(value = "/{id}") // 🎯 Mapeia para PUT com a variável 'id' na URL
    public ResponseEntity<Void> update(@RequestBody Post obj, @PathVariable String id) {
        // 2. Garante que a Entidade tenha o ID correto (vindo da URL)
        obj.setId(id);
        
        // 3. Chama o método update do Service para buscar, atualizar e salvar
        service.update(id, obj);
        
        // 4. Retorna o status 204 No Content para indicar sucesso sem corpo.
        // Se você quisesse retornar o objeto atualizado (prática aceitável),
        // o retorno seria ResponseEntity<Post> e o status 200 OK.
        return ResponseEntity.noContent().build();
    }

}
