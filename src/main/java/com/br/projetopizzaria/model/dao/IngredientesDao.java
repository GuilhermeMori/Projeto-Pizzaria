package com.br.projetopizzaria.model.dao;

import java.util.List;

import com.br.projetopizzaria.model.entities.Ingredientes;

public interface IngredientesDao {
	

	void insert(Ingredientes ingredientes);
	
	void update(Ingredientes ingredientes);
	
	void deleteById(Integer id);
	
	List<Ingredientes> findAll();

}
