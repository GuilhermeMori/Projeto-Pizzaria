package com.br.projetopizzaria.model.entities;

import java.io.Serializable;

public class Cadastro implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private String sobrenome;
	private String telefone;
	
	
	public Cadastro(Integer id, String nome, String sobrenome, String telefone) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
	}
	public Cadastro(String nome, String sobrenome, String telefone) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.telefone = telefone;
	}
	
	
	public Cadastro(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	