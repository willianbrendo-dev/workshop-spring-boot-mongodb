package com.willianbrendo.workshopmongo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.willianbrendo.workshopmongo.domain.User;
import com.willianbrendo.workshopmongo.repositories.UserRepository;

@Component
public class Instantiation  implements CommandLineRunner{

	private final UserRepository repository;
	
	public Instantiation(UserRepository repository) {
        this.repository = repository;
    }
	
	@Override
	public void run(String... args) throws Exception {
		repository.deleteAll(); // limpa antes, opcional

        repository.save(new User(null, "Willian", "willian@example.com"));
        repository.save(new User(null, "Maria", "maria@example.com"));
        repository.save(new User(null, "João", "joao@example.com"));
		
	}

}
