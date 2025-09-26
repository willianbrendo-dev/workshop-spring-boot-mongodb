package com.willianbrendo.workshopmongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.willianbrendo.workshopmongo.domain.User;

//@Repository é opcional aqui, mas ajuda na legibilidade
@Repository 
//🎯 Interface que estende MongoRepository: 
//<Entidade, TipoDaChavePrimária>
public interface UserRepository extends MongoRepository<User, String>{

	// Esta interface herda automaticamente todos os métodos CRUD básicos (findAll, findById, save, delete, etc.)
    // Você não precisa escrever nenhum código aqui!
}
