package com.br.projetopizzaria.model.dao;

import com.br.projetopizzaria.db.Db;
import com.br.projetopizzaria.model.dao.impl.CadastroDaoJDBC;
import com.br.projetopizzaria.model.dao.impl.IngredientesDaoJDBC;

public class DaoFactory {

	public static CadastroDao createClientesDao() {
		return new CadastroDaoJDBC(Db.getConecction());
	}
	
	public static IngredientesDao createIngredientesDao() {
		return new IngredientesDaoJDBC(Db.getConecction());
	}
}
