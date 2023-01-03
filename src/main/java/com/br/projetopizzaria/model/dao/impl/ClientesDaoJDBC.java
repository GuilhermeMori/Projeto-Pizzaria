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
import com.br.projetopizzaria.model.dao.ClientesDao;
import com.br.projetopizzaria.model.entities.Clientes;

public class ClientesDaoJDBC  implements ClientesDao{
	

	private Connection conn;
	
	public ClientesDaoJDBC(Connection conn) {
		this.conn = conn; 
	}

	@Override
	public void insert(Clientes clientes) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Clientes "
					+ "(id, nome, sobrenome, telefone) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, clientes.getId());
			st.setString(2, clientes.getNome());
			st.setString(3, clientes.getSobrenome());
			st.setString(4, clientes.getTelefone());
	
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
	public List<Clientes> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM Clientes ORDER BY Name");
			rs = st.executeQuery();

			List<Clientes> list = new ArrayList<>();

			while (rs.next()) {
				Clientes clientes = new Clientes();
				clientes.setId(rs.getInt("Id"));
				clientes.setNome(rs.getString("Name"));
				list.add(clientes);
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

	@Override
	public List<Clientes> findById(Clientes clientes) {
		return null;
	}

}
