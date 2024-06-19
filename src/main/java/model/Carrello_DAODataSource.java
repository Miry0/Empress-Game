package model;
//implementazione dell'interfaccia di Dao per la tabella "CARRELLO"; 
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

public class Carrello_DAODataSource implements IBeanDAO<Carrello_bean> {//implementiamo la classe bean degli utenti del catalogo che abbiamo creato

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
	private static final String TABLE_NAME = "CARRELLO"; //passiamo il carrello 

	@Override
	//serve per inserire una nuova query nella tabella CARRELLO	; 
	public synchronized void doSave(Carrello_bean Carrello) throws SQLException {

		//creiamo una variabile "connession" che contenga la connessione al DB; 
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		//prepariamo una query sql per l'inserimmento della nuoava riga nella tabella CARRELLO; 
		String insertSQL = "INSERT INTO " + Carrello_DAODataSource.TABLE_NAME
				+ " (n_ordine, id_utente, metodo_pagamento, totale, g_ordine, m_ordine, a_ordine) VALUES (?, ?, ?, ?, ?, ?, ?)"; //permettiamo l'inserimento di tuple nella table, attraverso una connessione tramite DAO+DataSource

	
		try {// cerchiamo di recuperare i dati salvati nel BD per i vari campi; 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, Carrello.get_n_ordine());
			preparedStatement.setInt(2, Carrello.get_id_utente());
			preparedStatement.setString(3, Carrello.get_metodo_pagamento());
			preparedStatement.setFloat(4, Carrello.get_totale());
			preparedStatement.setInt(5, Carrello.get_g_ordine());
			preparedStatement.setInt(6, Carrello.get_m_ordine());
			preparedStatement.setInt(7, Carrello.get_a_ordine());

			//eseguiamo l'inserimento della query recuperata; ; 
			preparedStatement.executeUpdate();

		} finally { //dopo l'eliminazione, effettuiamo un controllo e valutiamo se la connessione e la query sonon state chiuse/svuotate; 
			//se ciò non è stato fatto, lo facciamo manualmente; 
			try {
				if (preparedStatement != null)
					//chiudiamo la connessione con il Preparedstatment; 
					preparedStatement.close();
			} finally {
				if (connection != null)
					//chiudimao la connessione con il DB; ; 
					connection.close();
			}
		}
	}

	@Override
	//serve per eliminare una query dalla tabella CARRELLO; 
	public synchronized boolean doDelete(int code) throws SQLException {
		//stabiliamo una connessione con il DB; 
		Connection connection = null;
		//prepariamo una query per l'eliminazione di una tupla della tabella CARRELLO; 
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + Carrello_DAODataSource.TABLE_NAME + " WHERE CODE = ?";

		try {
			//ottentiamo una connessione con il DB; 
			connection = ds.getConnection();
			
			//salviamo i dati della query nella variabile 
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, code);
			
			//eseguiamo l'eliminazione della quesry; 
			result = preparedStatement.executeUpdate();

		} finally {//controlliamo se la connessione e la query sono state chiuse/svuotate
			try {
				//nel caso contrario, lo facciamo manualmente; 
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		//restituisce true sel aquery è stata eliminata, altrimenti fslse; 
		return (result != 0);
	}

	@Override
	public synchronized Collection<Carrello_bean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		//preprata query per la selezione di tutte le righe della tabella CARRELLO; 
		PreparedStatement preparedStatement = null;
		
		Collection<Carrello_bean> carrello = new LinkedList<Carrello_bean>();

		String selectSQL = "SELECT * FROM " + Carrello_DAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				//per ogni riga salvata in "rs", crea un oggetto Carrello_bean, imposta i valori con quelli salvati in "rs" e lo aggiunge alla collezione "carrello"; 
				Carrello_bean bean = new Carrello_bean();

				bean.set_n_ordine(rs.getInt("numero ordine"));
				bean.set_id_utente(rs.getInt("codice utente"));
				bean.set_metodo_pagamento(rs.getString("metodo di pagamento"));
				bean.set_totale(rs.getFloat("importo totale"));
				bean.set_g_ordine(rs.getInt("gg"));
				bean.set_m_ordine(rs.getInt("mm"));
				bean.set_a_ordine(rs.getInt("aaaa"));
				
				carrello.add(bean);
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
		return carrello; //restituisce la collezione costruita; 
	}
	
	@Override
	//serve per selezione una riga della tabella CARRELLO utilizzando la chiave
	public synchronized Carrello_bean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Carrello_bean bean = new Carrello_bean();//creiamo un nuovo bean, della classe Carrello
		String selectSQL = "SELECT * FROM " + Carrello_DAODataSource.TABLE_NAME + " WHERE CODE = ?";
		try {
			connection = ds.getConnection();	
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				//per ogni riga salvata in "rs", crea un oggetto Carrello_bean, imposta i valori con quelli salvati in "rs" e lo restituisce; 

				bean.set_n_ordine(rs.getInt("numero ordine"));
				//int userId = rs.getInt("codice utente");
				bean.set_n_ordine(rs.getInt("numero ordine"));
		        int userId = rs.getInt("codice utente");
		        bean.set_id_utente(userId);
				bean.set_metodo_pagamento(rs.getString("metodo di pagamento"));
				bean.set_totale(rs.getFloat("importo totale"));
				bean.set_g_ordine(rs.getInt("gg"));
				bean.set_m_ordine(rs.getInt("mm"));
				bean.set_a_ordine(rs.getInt("aaaa"));
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
		return bean; //restituisce riga trovata; 
	}
}

