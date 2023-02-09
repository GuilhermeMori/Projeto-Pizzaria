package com.br.projetopizzaria.model.entities;

import java.io.Serializable;

public class SaboresIngredientes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer Ingredientes1;
	private Integer Ingredientes2;
	private Integer Ingredientes3;
	private Integer Ingredientes4;
	private Integer Ingredientes5;
	
	public SaboresIngredientes() {
		
	}
	

	public SaboresIngredientes(Integer ingredientes1, Integer ingredientes2, Integer ingredientes3,
			Integer ingredientes4, Integer ingredientes5) {
		Ingredientes1 = ingredientes1;
		Ingredientes2 = ingredientes2;
		Ingredientes3 = ingredientes3;
		Ingredientes4 = ingredientes4;
		Ingredientes5 = ingredientes5;
	}
	
	

	public SaboresIngredientes(Integer id, Integer ingredientes1, Integer ingredientes2, Integer ingredientes3,
			Integer ingredientes4, Integer ingredientes5) {
		this.id = id;
		Ingredientes1 = ingredientes1;
		Ingredientes2 = ingredientes2;
		Ingredientes3 = ingredientes3;
		Ingredientes4 = ingredientes4;
		Ingredientes5 = ingredientes5;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIngredientes1() {
		return Ingredientes1;
	}

	public void setIngredientes1(Integer ingredientes1) {
		Ingredientes1 = ingredientes1;
	}

	public Integer getIngredientes2() {
		return Ingredientes2;
	}

	public void setIngredientes2(Integer ingredientes2) {
		Ingredientes2 = ingredientes2;
	}

	public Integer getIngredientes3() {
		return Ingredientes3;
	}

	public void setIngredientes3(Integer ingredientes3) {
		Ingredientes3 = ingredientes3;
	}

	public Integer getIngredientes4() {
		return Ingredientes4;
	}

	public void setIngredientes4(Integer ingredientes4) {
		Ingredientes4 = ingredientes4;
	}

	public Integer getIngredientes5() {
		return Ingredientes5;
	}

	public void setIngredientes5(Integer ingredientes5) {
		Ingredientes5 = ingredientes5;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}	

	