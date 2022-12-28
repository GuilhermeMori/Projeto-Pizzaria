package com.br.projetopizzaria.service;

import java.util.List;

import com.br.projetopizzaria.domain.model.Clientes;

public interface ClientesService {
	
	void insert(Clientes clientes);
	
	List<Clientes> findAll();

}
