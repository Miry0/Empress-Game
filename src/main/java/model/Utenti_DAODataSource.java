package model;
//implementazione dell'interfaccia di Dao per la tabella "UTENTI"; 
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

public class Utenti_DAODataSource implements IBeanDAO<Utenti_bean> {//implementiamo la classe bean degli utenti del catalogo che abbiamo creato

	private static DataSource ds; 
/*
	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/storage");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
*/
	private static final String TABLE_NAME = "UTENTI"; //passiamo il nome della lista degli utenti su Mysql

	@Override
	public synchronized void doSave(Utenti_bean Utenti) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + Utenti_DAODataSource.TABLE_NAME
				+ " (nome, cognome, _password, tipo, g_nascita, m_nascita, a_nascita) VALUES (?, ?, ?, ?, ?, ?, ?)"; //permettiamo l'inserimento di tuple nella table, attraverso una cinnessione tramite DAO+DataSource

		try { //cerchiamo una connessione del Db e recuperiiamo i dati salvati al suo interno
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, Utenti.get_nome());
			preparedStatement.setString(2, Utenti.get_cognome());
			preparedStatement.setString(3, Utenti.get_password());
			preparedStatement.setString(4, Utenti.get_tipo());
			preparedStatement.setInt(5, Utenti.get_g_nascita());
			preparedStatement.setInt(6, Utenti.get_m_nascita());
			preparedStatement.setInt(7, Utenti.get_a_nascita());

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

		String deleteSQL = "DELETE FROM " + Utenti_DAODataSource.TABLE_NAME + " WHERE CODE = ?";

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
	public synchronized Collection<Utenti_bean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Utenti_bean> utenti = new LinkedList<Utenti_bean>();

		String selectSQL = "SELECT * FROM " + Utenti_DAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Utenti_bean bean = new Utenti_bean();

				bean.set_id_utente(rs.getInt("id_utente"));
				bean.set_nome(rs.getString("nome"));
				bean.set_cognome(rs.getString("cognome"));
				bean.set_password(rs.getString("password"));
				bean.set_tipo(rs.getString("tipo"));
				bean.set_g_nascita(rs.getInt("gg"));
				bean.set_m_nascita(rs.getInt("mm"));
				bean.set_a_nascita(rs.getInt("aaaa"));
				
				utenti.add(bean);
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
		return utenti;
	}
	
	@Override
	public synchronized Utenti_bean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Utenti_bean bean = new Utenti_bean();//creiamo un nuovo bean della classe Utenti
		String selectSQL = "SELECT * FROM " + Utenti_DAODataSource.TABLE_NAME + " WHERE CODE = ?";
		try {
			connection = ds.getConnection();	
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				bean.set_id_utente(rs.getInt("id_utente"));
				bean.set_nome(rs.getString("nome"));
				bean.set_cognome(rs.getString("cognome"));
				bean.set_password(rs.getString("password"));
				bean.set_tipo(rs.getString("tipo"));
				bean.set_g_nascita(rs.getInt("gg"));
				bean.set_m_nascita(rs.getInt("mm"));
				bean.set_a_nascita(rs.getInt("aaaa"));
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




