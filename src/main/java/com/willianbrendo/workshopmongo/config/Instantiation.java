package com.willianbrendo.workshopmongo.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.willianbrendo.workshopmongo.domain.Post;
import com.willianbrendo.workshopmongo.domain.User;
import com.willianbrendo.workshopmongo.dto.AuthorDTO;
import com.willianbrendo.workshopmongo.dto.CommentDTO;
import com.willianbrendo.workshopmongo.repositories.PostRepository;
import com.willianbrendo.workshopmongo.repositories.UserRepository;

@Component
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		postRepository.deleteAll();// limpa antes, opcional

		User willian = new User(null, "Willian", "willian@example.com");
		User maria = new User(null, "Maria", "maria@example.com");
		User joao = new User(null, "João", "joao@example.com");

		userRepository.saveAll(Arrays.asList(willian, maria, joao));
		

		Post post1 = new Post(null, Instant.now(), "Partiu Viagem!", "Vou viajar para São Paulo, Abraços",
				new AuthorDTO(willian));
		Post post2 = new Post(null, Instant.now().minusSeconds(86400), // Ex: um dia atrás
				"Bom dia!", "Acordei hoje feliz, cheio de energia!", new AuthorDTO(maria) // Autor: Embutindo o DTO do Alex
		);
		
		CommentDTO comen1 = new CommentDTO("Boa viagem!!", Instant.now(), new AuthorDTO(joao));
		post1.getComments().add(comen1);
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		willian.getPosts().add(post1);
		maria.getPosts().add(post2);
		
		userRepository.saveAll(Arrays.asList(willian, maria));

	}

}
