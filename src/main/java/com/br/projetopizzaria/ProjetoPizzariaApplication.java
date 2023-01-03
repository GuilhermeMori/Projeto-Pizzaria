package com.br.projetopizzaria;

import java.sql.Connection;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.br.projetopizzaria.db.Db;
import com.br.projetopizzaria.model.dao.ClientesDao;
import com.br.projetopizzaria.model.dao.DaoFactory;
import com.br.projetopizzaria.model.entities.Clientes;

@SpringBootApplication
public class ProjetoPizzariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoPizzariaApplication.class, args);
		
		Connection conn = Db.getConecction();
		
		Scanner sc = new Scanner(System.in);
		
		ClientesDao clientesdao = DaoFactory.createClientesDao();
		
		System.out.println("----TESTE 1 ------");
		System.out.println("Digite o nome");
		String nome = sc.next();
		System.out.println("Digite o sobrenome");
		String sobrenome = sc.next();
		System.out.println("Digite o telefone");
		String telefone = sc.next();
		Clientes newClientes = new Clientes(nome,sobrenome,telefone);
		clientesdao.insert(newClientes);
		System.out.println("Inserted ! New id :"+ newClientes.getId()+" "+ newClientes.getNome()+""+newClientes.getSobrenome());
	}

}
