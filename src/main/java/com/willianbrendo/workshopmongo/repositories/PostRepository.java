package com.willianbrendo.workshopmongo.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.willianbrendo.workshopmongo.domain.Post;

//@Repository é opcional aqui, mas ajuda na legibilidade
@Repository 
//🎯 Interface que estende MongoRepository: 
//<Entidade, TipoDaChavePrimária>
public interface PostRepository extends MongoRepository<Post, String>{

	// Esta interface herda automaticamente todos os métodos CRUD básicos (findAll, findById, save, delete, etc.)
    // Você não precisa escrever nenhum código aqui!
	
	/**
     * Busca todos os Posts cujo título contenha a string fornecida (ignorando case).
     * * Convenção do Spring Data:
     * - findBy: Indica uma consulta de busca.
     * - Title: É o nome do campo na entidade Post (post.getTitle()).
     * - Containing: Operador que significa "onde o campo contém a string".
     * - IgnoreCase: Adiciona a opção case-insensitive (ignorando maiúsculas/minúsculas).
     * * @param text A string de busca (ex: "bom dia").
     * @return Uma lista de Posts que atendem ao critério.
     */
    List<Post> findByTitleContainingIgnoreCase(String text);
}
