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
import com.br.projetopizzaria.model.dao.SaboresIngredientesDao;
import com.br.projetopizzaria.model.entities.Cadastro;
import com.br.projetopizzaria.model.entities.SaboresIngredientes;

public class SaboresIngredientesDaoJDBC implements SaboresIngredientesDao{

	private Connection conn;
	
	public SaboresIngredientesDaoJDBC (Connection conn) {
		this.conn = conn; 
	}
	
	@Override
	public void insert(SaboresIngredientes sabores) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO Sabores_ingredientes "
					+ "(idingredientes1, idingredientes2, idingredientes3, idingredientes4,idingredientes5)"
					+ "VALUES "
					+ "(?,?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, sabores.getIngredientes1());
			st.setInt(2,sabores.getIngredientes2());
			st.setInt(3,sabores.getIngredientes3());
			st.setInt(4,sabores.getIngredientes4());
			st.setInt(5,sabores.getIngredientes5());
		
	
			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					sabores.setId(id);
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
	public void update(SaboresIngredientes sabores) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE sabores_ingredientes "
					+ "SET idingredientes1= ?, idingredientes2 = ?, idingredientes3 = ?, idingredientes4 = ?, idingredientes5 = ? "
					+ "WHERE Id = ?");
			
			st.setInt(1, sabores.getIngredientes1());
			st.setInt(2, sabores.getIngredientes2());
			st.setInt(3, sabores.getIngredientes3());
			st.setInt(4, sabores.getIngredientes4());
			st.setInt(5, sabores.getIngredientes5());
			st.setInt(6, sabores.getId());

			
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
				"DELETE FROM Sabores_ingredientes WHERE Id = ?");

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
	public List<SaboresIngredientes> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM Sabores_ingredientes" );
			rs = st.executeQuery();

			List<SaboresIngredientes> list = new ArrayList<>();

			while (rs.next()) {
				SaboresIngredientes obj = new SaboresIngredientes();
				obj.setId(rs.getInt("Id"));
				obj.setIngredientes1(rs.getInt("idingredientes1"));
				obj.setIngredientes2(rs.getInt("idingredientes2"));
				obj.setIngredientes3(rs.getInt("idingredientes3"));
				obj.setIngredientes4(rs.getInt("idingredientes4"));
				obj.setIngredientes5(rs.getInt("idingredientes5"));
				
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
