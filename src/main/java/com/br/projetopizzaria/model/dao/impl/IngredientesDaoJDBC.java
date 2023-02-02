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
import com.br.projetopizzaria.model.dao.IngredientesDao;
import com.br.projetopizzaria.model.entities.Cadastro;
import com.br.projetopizzaria.model.entities.Ingredientes;

public class IngredientesDaoJDBC  implements IngredientesDao{
	

	private Connection conn;
	
	public IngredientesDaoJDBC(Connection conn) {
		this.conn = conn; 
	}

	@Override
	public void insert(Ingredientes ingredientes) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Ingredientes "
					+ "(Descricao)"
					+ "VALUES "
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, ingredientes.getDescricao());
	
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					ingredientes.setId(id);
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
	public void update(Ingredientes ingredientes) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE Ingredientes "
					+ "SET descricao = ?"
					+ "WHERE Id = ?");
			
			st.setString(1, ingredientes.getDescricao());
			st.setLong(2, ingredientes.getId());
			
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
				"DELETE FROM Ingredientes WHERE Id = ?");

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
	public List<Ingredientes> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM Ingredientes");
			rs = st.executeQuery();

			List<Ingredientes> list = new ArrayList<>();

			while (rs.next()) {
				Ingredientes obj = new Ingredientes();
				obj.setId(rs.getInt("Id"));
				obj.setDescricao(rs.getString("Descricao"));
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


