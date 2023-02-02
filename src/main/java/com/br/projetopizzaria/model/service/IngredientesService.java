package com.br.projetopizzaria.model.service;

import java.util.List;

import com.br.projetopizzaria.model.dao.DaoFactory;
import com.br.projetopizzaria.model.dao.IngredientesDao;
import com.br.projetopizzaria.model.entities.Ingredientes;

public class IngredientesService {
	
	private IngredientesDao dao = DaoFactory.createIngredientesDao();

	public List<Ingredientes> findAll() {
		return dao.findAll();
	}

	public void saveOrUpdate(Ingredientes ingredientes) {
		if (ingredientes.getId() == null) {
			dao.insert(ingredientes);
		} else {
			dao.update(ingredientes);
		}
	}

	public void remove(Ingredientes ingredientes) {
		dao.deleteById(ingredientes.getId());
	}
}
