package com.br.projetopizzaria.model.entities;

import java.io.Serializable;

public class SaboresIngredientes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String descricao;
	private Integer idIngredientes;
	
	public SaboresIngredientes() {
	}
	
	public SaboresIngredientes(Integer id, String descricao, Integer idIngredientes) {
		this.id = id;
		this.descricao = descricao;
		this.idIngredientes = idIngredientes;
	}
	
	public SaboresIngredientes(String descricao, Integer idIngredientes) {
		this.descricao = descricao;
		this.idIngredientes = idIngredientes;
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

	public Integer getIdIngredientes() {
		return idIngredientes;
	}

	public void setIdIngredientes(Integer idIngredientes) {
		this.idIngredientes = idIngredientes;
	}
	


}
