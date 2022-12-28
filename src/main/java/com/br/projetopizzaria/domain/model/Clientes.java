package com.br.projetopizzaria.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "Clientes")
public class Clientes {
	@Id
	@GeneratedValue
	private long id;
	private String nome;
	private String sobrenome;
	private String telefone;
	
	public Clientes(long id, String nome, String sobrenome, String telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	
	
	
}
	