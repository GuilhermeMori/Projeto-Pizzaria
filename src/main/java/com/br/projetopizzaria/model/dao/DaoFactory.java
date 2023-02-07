package com.br.projetopizzaria.model.dao;

import com.br.projetopizzaria.db.Db;
import com.br.projetopizzaria.model.dao.impl.CadastroDaoJDBC;
import com.br.projetopizzaria.model.dao.impl.IngredientesDaoJDBC;
import com.br.projetopizzaria.model.dao.impl.SaboresIngredientesDaoJDBC;

public class DaoFactory {

	public static CadastroDao createClientesDao() {
		return new CadastroDaoJDBC(Db.getConecction());
	}
	
	public static IngredientesDao createIngredientesDao() {
		return new IngredientesDaoJDBC(Db.getConecction());
	}
	
	public static SaboresIngredientesDao createSaboresIngredientesDao() {
		return new SaboresIngredientesDaoJDBC(Db.getConecction());
	}
}
