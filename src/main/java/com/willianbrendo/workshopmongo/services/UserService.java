package com.willianbrendo.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willianbrendo.workshopmongo.domain.User;
import com.willianbrendo.workshopmongo.repositories.UserRepository;
import com.willianbrendo.workshopmongo.services.exceptions.ObjectNotFoundException;

@Service // 🎯 Anotação que registra esta classe como um componente de serviço do Spring
public class UserService {

	// Injeção de dependência do Repositório
    @Autowired 
    private UserRepository repository; 
    // O Spring inicializa automaticamente o 'repository' quando o UserService é criado.

    /**
     * Retorna todos os usuários cadastrados no banco de dados MongoDB.
     * @return Uma lista de objetos User.
     */
    public List<User> findAll() {
        // O método findAll() é herdado do MongoRepository e busca todos os documentos da coleção "users".
        return repository.findAll();
    }
    
    /**
     * Busca um usuário pelo seu ID, lançando exceção se não for encontrado.
     * @param id O ID (String) do usuário a ser buscado.
     * @return O objeto User, se encontrado.
     */
    public User findById(String id) {
        // O findById retorna um Optional<User>
        Optional<User> obj = repository.findById(id);
        
        // 🎯 Se o Optional estiver vazio (ID não existe), lança ResourceNotFoundException (404)
        // Se contiver um User, retorna o objeto User.
        return obj.orElseThrow(() -> new ObjectNotFoundException(id));
    }
}
