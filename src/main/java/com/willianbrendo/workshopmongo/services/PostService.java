package com.willianbrendo.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willianbrendo.workshopmongo.domain.Post;
import com.willianbrendo.workshopmongo.domain.User;
import com.willianbrendo.workshopmongo.repositories.PostRepository;
import com.willianbrendo.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service // 🎯 Anotação que registra esta classe como um componente de serviço do Spring
public class PostService {

	// Injeção de dependência do Repositório
    @Autowired 
    private PostRepository repository; 
    // O Spring inicializa automaticamente o 'repository' quando o PostService é criado.

    /**
     * Retorna todos os usuários cadastrados no banco de dados MongoDB.
     * @return Uma lista de objetos Post.
     */
    public List<Post> findAll() {
        // O método findAll() é herdado do MongoRepository e busca todos os documentos da coleção "users".
        return repository.findAll();
    }
    
    /**
     * Busca um usuário pelo seu ID, lançando exceção se não for encontrado.
     * @param id O ID (String) do usuário a ser buscado.
     * @return O objeto User, se encontrado.
     */
    public Post findById(String id) {
        // O findById retorna um Optional<User>
        Optional<Post> obj = repository.findById(id);
        
        // 🎯 Se o Optional estiver vazio (ID não existe), lança ResourceNotFoundException (404)
        // Se contiver um User, retorna o objeto User.
        return obj.orElseThrow(() -> new ObjectNotFoundException(id));
    }
    
    /**
     * Insere um novo usuário no banco de dados.
     * @param obj O objeto User (Entidade) a ser salvo.
     * @return O objeto User salvo, contendo o ID gerado pelo MongoDB.
     */
    public Post insert(Post obj) {
        // 1. Antes de salvar, garantimos que o ID do objeto é nulo.
        // O MongoDB irá gerar um novo ID.
        obj.setId(null); 
        
        // 2. O método save() do MongoRepository realiza o INSERT quando o ID é nulo.
        return repository.save(obj);
    }
    
    /**
     * Deleta um usuário pelo ID, verificando primeiro se ele existe.
     * @param id O ID (String) do usuário a ser deletado.
     */
    public void delete(String id) {
        
        // 1. Garante o tratamento de erro 404: 
        // Usamos o findById para verificar a existência. Se não encontrar,
        // o findById lança a ResourceNotFoundException.
        findById(id); 
        
        // 2. Se o findById não lançou exceção, o recurso existe e pode ser deletado.
        repository.deleteById(id);
        
        // NOTA: Em bancos de dados relacionais, aqui seria o ponto para tratar 
        // a DataIntegrityViolationException (erro 400), mas no MongoDB NoSQL puro,
        // a exclusão de um documento pai não é automaticamente impedida pela FK,
        // então a lógica fica mais simples.
    }
    
    
    /**
     * Atualiza um usuário existente no banco de dados.
     * @param id O ID (String) do usuário a ser atualizado.
     * @param obj O objeto User com os novos dados.
     * @return O objeto User atualizado.
     */
    public Post update(String id, Post obj) {
        
        // 1. Busca a Entidade existente. Se não encontrar, lança 404.
        // Usamos o findById que já implementamos para garantir o tratamento 404.
        Post entity = findById(id); 
        
        // 2. Copia os dados do objeto 'obj' (que veio do Controller) para a 'entity' (que veio do banco)
        updateData(entity, obj);
        
        // 3. O método save() do MongoRepository realiza o UPDATE quando o ID não é nulo.
        return repository.save(entity);
    }

    /**
     * Método auxiliar privado para copiar os dados relevantes do objeto de origem (obj) 
     * para o objeto destino (entity), que está sendo monitorado.
     */
    private void updateData(Post entity, Post obj) {
        entity.setTitle(obj.getTitle());
        entity.setDate(obj.getDate());
        entity.setBody(obj.getBody());
        entity.setAuthor(obj.getAuthor());
    }
}
