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

public class Game_DAODataSource implements IBeanDAO<Game_bean> {//implementiamo la classe bean degli utenti del catalogo che abbiamo creato

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
	private static final String TABLE_NAME = "GIOCHI"; //passiamo il nome della lista degli utenti su Mysql

	@Override
	public synchronized void doSave(Game_bean Giochi) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + Game_DAODataSource.TABLE_NAME
				+ " (id_gioco, nome, piattaforma, genere, prezzo, g_uscita, m_uscita, a_uscita) VALUES (?, ?, ?, ?, ?, ?, ?)"; //permettiamo l'inserimento di tuple nella table, attraverso una cinnessione tramite DAO+DataSource

		try { //cerchiamo una connessione del Db e recuperiiamo i dati salvati al suo interno
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, Giochi.get_id_gioco());
			preparedStatement.setString(1, Giochi.get_nome());
			preparedStatement.setString(2, Giochi.get_piattaforma());
			preparedStatement.setString(3, Giochi.get_genere());
			preparedStatement.setFloat(4, Giochi.get_prezzo());
			preparedStatement.setInt(5, Giochi.get_g_uscita());
			preparedStatement.setInt(6, Giochi.get_m_uscita());
			preparedStatement.setInt(7, Giochi.get_a_uscita());

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

		String deleteSQL = "DELETE FROM " + Game_DAODataSource.TABLE_NAME + " WHERE CODE = ?";

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
	public synchronized Collection<Game_bean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Collection<Game_bean> giochi = new LinkedList<Game_bean>();

		String selectSQL = "SELECT * FROM " + Game_DAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Game_bean bean = new Game_bean();

				bean.set_id_gioco(rs.getInt("code identificativo del gioco"));
				bean.set_nome(rs.getString("nome"));
				bean.set_piattaforma(rs.getString("piattaforma su cui è utitlizzabile la key"));
				bean.set_genere(rs.getString("genere"));
				bean.set_prezzo(rs.getFloat("prezzo"));
				bean.set_g_uscita(rs.getInt("gg"));
				bean.set_m_uscita(rs.getInt("mm"));
				bean.set_a_uscita(rs.getInt("aaaa"));
				
				giochi.add(bean);
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
		return giochi;
	}
	
	@Override
	public synchronized Game_bean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Game_bean bean = new Game_bean();//creiamo un nuovo bean della classe Utenti
		String selectSQL = "SELECT * FROM " + Game_DAODataSource.TABLE_NAME + " WHERE CODE = ?";
		try {
			connection = ds.getConnection();	
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				bean.set_id_gioco(rs.getInt("code identificativo del gioco"));
				bean.set_nome(rs.getString("nome"));
				bean.set_piattaforma(rs.getString("piattaforma su cui è utitlizzabile la key"));
				bean.set_genere(rs.getString("genere"));
				bean.set_prezzo(rs.getFloat("prezzo"));
				bean.set_g_uscita(rs.getInt("gg"));
				bean.set_m_uscita(rs.getInt("mm"));
				bean.set_a_uscita(rs.getInt("aaaa"));
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


