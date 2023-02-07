package com.br.projetopizzaria.model.service;

import java.util.List;

import com.br.projetopizzaria.model.dao.DaoFactory;
import com.br.projetopizzaria.model.dao.SaboresIngredientesDao;
import com.br.projetopizzaria.model.entities.SaboresIngredientes;

public class SaboresIngredientesService {
	
	private SaboresIngredientesDao dao = DaoFactory.createSaboresIngredientesDao();
	
	public List<SaboresIngredientes> findAll() {
		return dao.findAll();
	}

	public void saveOrUpdate(SaboresIngredientes sabores) {
		if (sabores.getId() == null) {
			dao.insert(sabores);
		} else {
			dao.update(sabores);
		}
	}

	public void remove(SaboresIngredientes sabores) {
		dao.deleteById(sabores.getId());
	}

}
