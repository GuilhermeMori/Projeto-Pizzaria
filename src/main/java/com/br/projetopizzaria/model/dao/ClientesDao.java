package com.br.projetopizzaria.model.dao;

import java.util.List;

import com.br.projetopizzaria.model.entities.Clientes;

public interface ClientesDao {
	
	void insert(Clientes clientes);
	
	List<Clientes> findAll();
	
	List<Clientes> findById(Clientes clientes);

}
