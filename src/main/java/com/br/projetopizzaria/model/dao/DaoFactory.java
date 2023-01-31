package com.br.projetopizzaria.model.dao;

import com.br.projetopizzaria.db.Db;
import com.br.projetopizzaria.model.dao.impl.CadastroDaoJDBC;

public class DaoFactory {

	public static CadastroDao createClientesDao() {
		return new CadastroDaoJDBC(Db.getConecction());
	}
}
