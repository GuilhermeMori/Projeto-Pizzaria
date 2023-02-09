package com.br.projetopizzaria.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.br.projetopizzaria.db.Db;
import com.br.projetopizzaria.db.DbException;
import com.br.projetopizzaria.model.dao.CadastroDao;
import com.br.projetopizzaria.model.entities.Cadastro;

public class CadastroDaoJDBC  implements CadastroDao{
	

	private Connection conn;
	
	public CadastroDaoJDBC(Connection conn) {
		this.conn = conn; 
	}

	@Override
	public void insert(Cadastro clientes) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Clientes "
					+ "(nome, sobrenome, telefone) "
					+ "VALUES "
					+ "(?,?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, clientes.getNome());
			st.setString(2, clientes.getSobrenome());
			st.setString(3, clientes.getTelefone());
	
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					clientes.setId(id);
				}
				Db.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			Db.closeStatement(st);
		}
	}

	
	@Override
	public void update(Cadastro clientes) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE Clientes "
					+ "SET Nome = ?, Sobrenome = ?, Telefone = ?"
					+ "WHERE Id = ?");
			
			st.setString(1, clientes.getNome());
			st.setString(2, clientes.getSobrenome());
			st.setString(3, clientes.getTelefone());
			st.setLong(4, clientes.getId());
			
			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			Db.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM Clientes WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			Db.closeStatement(st);
		}
	}

	@Override
	public List<Cadastro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM Clientes");
			rs = st.executeQuery();

			List<Cadastro> list = new ArrayList<>();

			while (rs.next()) {
				Cadastro obj = new Cadastro();
				obj.setId(rs.getInt("Id"));
				obj.setNome(rs.getString("Nome"));
				obj.setSobrenome(rs.getString("Sobrenome"));
				obj.setTelefone(rs.getNString("telefone"));
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			Db.closeStatement(st);
			Db.closeResultSet(rs);
		}
	}
}
