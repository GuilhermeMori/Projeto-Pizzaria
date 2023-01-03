package com.br.projetopizzaria.model.dao;

import com.br.projetopizzaria.db.Db;
import com.br.projetopizzaria.model.dao.impl.ClientesDaoJDBC;

public class DaoFactory {

	public static ClientesDao createClientesDao() {
		return new ClientesDaoJDBC(Db.getConecction());
	}
}
