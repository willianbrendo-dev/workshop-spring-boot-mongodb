package com.willianbrendo.workshopmongo.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users") // 🎯 Anotação que mapeia esta classe para uma coleção chamada "users" no MongoDB
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id // 🎯 Define este atributo como a chave primária (ID) do Documento MongoDB
    private String id; // No MongoDB, o ID padrão é um String/ObjectId

	private String name;
    private String email;
    
 // 🎯 NOVO RELACIONAMENTO REFERENCIADO: Lista de Posts
    // @DBRef: Indica que esta é uma referência. O Spring só carrega esses posts 
    //         quando você explicitamente pedir (lazy loading por padrão).
    // mappedBy="author": Esta anotação, embora mais comum no JPA, é usada aqui por 
    //                     convenção para indicar que o mapeamento é feito pelo lado do 'author' no Post.
    @DBRef(lazy = true) // lazy = true é o padrão para @DBRef.
    private List<Post> posts = new ArrayList<>(); // Inicializa a lista para evitar NullPointerException
    
    
    public User() {
    }

	public User(String id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
}
