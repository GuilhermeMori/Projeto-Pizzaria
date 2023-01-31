package com.br.projetopizzaria.model.dao;

import java.util.List;

import com.br.projetopizzaria.model.entities.Cadastro;

public interface CadastroDao {
	
	void insert(Cadastro clientes);
	
	void update(Cadastro clientes);
	
	void deleteById(Integer id);
	
	List<Cadastro> findAll();
	
}
