package com.br.projetopizzaria.model.service;

import java.util.List;

import com.br.projetopizzaria.model.dao.CadastroDao;
import com.br.projetopizzaria.model.dao.DaoFactory;
import com.br.projetopizzaria.model.entities.Cadastro;

public class AlterarDadosService {
	
	private CadastroDao dao = DaoFactory.createClientesDao();

	public List<Cadastro> findAll() {
		return dao.findAll();
	}

	public void saveOrUpdate(Cadastro cadastro) {
		if (cadastro.getId() == null) {
			dao.insert(cadastro);
		} else {
			dao.update(cadastro);
		}
	}

	public void remove(Cadastro cadastro) {
		dao.deleteById(cadastro.getId());
	}
}
