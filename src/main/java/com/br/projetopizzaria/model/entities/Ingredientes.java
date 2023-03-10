package com.br.projetopizzaria.model.entities;

import java.io.Serializable;

public class Ingredientes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String descricao;

	
	public Ingredientes() {
		
	}
	
	public Ingredientes(String descricao) {
		this.descricao = descricao;
	}
	
	
	public Ingredientes(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}
	
	
	
}
