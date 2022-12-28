package com.br.projetopizzaria.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.projetopizzaria.domain.model.Clientes;
import com.br.projetopizzaria.domain.model.ClientesRepository;
import com.br.projetopizzaria.service.ClientesService;

@Service
public class ClientesServiceImpl  implements ClientesService{
	
	@Autowired
	private ClientesRepository repository;

	@Override
	public void insert(Clientes clientes) {
		repository.save(clientes);	
	}

	@Override
	public List<Clientes> findAll() {
		List<Clientes> clientes = repository.findAll();
		return clientes;
	}

}
