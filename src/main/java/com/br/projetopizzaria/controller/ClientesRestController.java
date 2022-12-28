package com.br.projetopizzaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.projetopizzaria.domain.model.Clientes;
import com.br.projetopizzaria.service.ClientesService;

@CrossOrigin
@RestController
public class ClientesRestController {
	
	@Autowired
	private ClientesService businessLayer;
	
	@PostMapping("clientes")
	public ResponseEntity<Clientes> insert(@RequestBody Clientes clientes){
		businessLayer.insert(clientes);
		return ResponseEntity.ok(clientes);
	}
	@GetMapping("base")
	public ResponseEntity<List<Clientes>>findAll(){
		List<Clientes> clientes = businessLayer.findAll();
		return ResponseEntity.ok(clientes);
	}
	

}
