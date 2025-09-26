package com.willianbrendo.workshopmongo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.willianbrendo.workshopmongo.domain.User;
import com.willianbrendo.workshopmongo.repositories.UserRepository;

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
}
