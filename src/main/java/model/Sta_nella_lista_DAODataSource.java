package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Sta_nella_lista_DAODataSource implements IBeanDAO<Sta_nella_lista_bean> {

	private static DataSource ds;

	private static final String TABLE_NAME = "sta_nella_lista";

	@Override
	public synchronized void doSave(Sta_nella_lista_bean Sta_nella_lista) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + Sta_nella_lista_DAODataSource.TABLE_NAME
				+ " (id_lista, nome_utente, id_gioco) VALUES (?, ?, ?)";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, Sta_nella_lista.get_id_lista());
			preparedStatement.setString(2, Sta_nella_lista.get_nome_utente());
			preparedStatement.setInt(3, Sta_nella_lista.get_id_gioco());

			preparedStatement.executeUpdate();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
	}

	@Override
	public synchronized boolean doDelete(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + Sta_nella_lista_DAODataSource.TABLE_NAME + " WHERE CODE = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, code);

			result = preparedStatement.executeUpdate();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return (result != 0);
	}

	@Override
	public synchronized Collection<Sta_nella_lista_bean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Sta_nella_lista_bean> Sta_nella_lista = new LinkedList<Sta_nella_lista_bean>();

		String selectSQL = "SELECT * FROM " + Sta_nella_lista_DAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Sta_nella_lista_bean bean = new Sta_nella_lista_bean();

				bean.set_id_lista(rs.getInt("id_lista"));
				bean.set_nome_utente(rs.getString("nome_utente"));
				bean.set_id_gioco(rs.getInt("id_gioco"));

				Sta_nella_lista.add(bean);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return Sta_nella_lista;
	}

	@Override
	public synchronized Sta_nella_lista_bean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Sta_nella_lista_bean bean = new Sta_nella_lista_bean();

		String selectSQL = "SELECT * FROM " + Sta_nella_lista_DAODataSource.TABLE_NAME + " WHERE CODE = ?";

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.set_id_lista(rs.getInt("id_lista"));
				bean.set_nome_utente(rs.getString("nome_utente"));
				bean.set_id_gioco(rs.getInt("id_gioco"));
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
				
			}
		}
		return bean;
	}
}
