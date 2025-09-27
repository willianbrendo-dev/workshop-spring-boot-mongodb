package com.willianbrendo.workshopmongo.dto;

import java.io.Serializable;
import java.util.Objects;

import com.willianbrendo.workshopmongo.domain.User;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id; 
    private String name;
    private String email;
    
    public UserDTO() {
    }
    
    /**
     * Construtor que recebe um objeto User (entidade) e copia os dados para o DTO.
     * Esta é a forma ideal de criar o DTO.
     * @param obj A entidade User.
     */
    public UserDTO(User obj) {
        this.id = obj.getId();
        this.name = obj.getName();
        this.email = obj.getEmail();
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
		UserDTO other = (UserDTO) obj;
		return Objects.equals(id, other.id);
	}
}
