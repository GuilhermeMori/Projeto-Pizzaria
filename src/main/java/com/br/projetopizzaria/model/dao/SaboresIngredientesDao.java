package com.br.projetopizzaria.model.dao;

import java.util.List;

import com.br.projetopizzaria.model.entities.SaboresIngredientes;

public interface SaboresIngredientesDao {
	
	void insert(SaboresIngredientes sabores);
	
	void update(SaboresIngredientes sabores);
	
	void deleteById(Integer id);
	
	List<SaboresIngredientes> findAll();

}
