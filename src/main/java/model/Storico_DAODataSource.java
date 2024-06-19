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

public class Storico_DAODataSource implements IBeanDAO<Storico_bean> {//implementiamo la classe bean degli utenti del catalogo che abbiamo creato

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
	private static final String TABLE_NAME = "STORICO"; //passiamo il carrello 

	@Override
	//serve per inserire una nuova query nella tabella CARRELLO	; 
	public synchronized void doSave(Storico_bean Storico) throws SQLException {

		//creiamo una variabile "connession" che contenga la connessione al DB; 
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		//prepariamo una query sql per l'inserimmento della nuoava riga nella tabella CARRELLO; 
		String insertSQL = "INSERT INTO " + Storico_DAODataSource.TABLE_NAME
				+ " (n_ordine) VALUES (?)"; //permettiamo l'inserimento di tuple nella table, attraverso una connessione tramite DAO+DataSource

	
		try {// cerchiamo di recuperare i dati salvati nel BD per i vari campi; 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, Storico.get_n_ordine());
		;

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

		String deleteSQL = "DELETE FROM " + Storico_DAODataSource.TABLE_NAME + " WHERE CODE = ?";

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
	public synchronized Collection<Storico_bean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		//preprata query per la selezione di tutte le righe della tabella CARRELLO; 
		PreparedStatement preparedStatement = null;
		
		Collection<Storico_bean> storico = new LinkedList<Storico_bean>();

		String selectSQL = "SELECT * FROM " + Storico_DAODataSource.TABLE_NAME;

		if (order != null && !order.equals("")) {
			selectSQL += " ORDER BY " + order;
		}

		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				//per ogni riga salvata in "rs", crea un oggetto Carrello_bean, imposta i valori con quelli salvati in "rs" e lo aggiunge alla collezione "carrello"; 
				Storico_bean bean = new Storico_bean();

				bean.set_n_ordine(rs.getInt("numero ordine"));

				storico.add(bean);
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
		return storico; //restituisce la collezione costruita; 
	}
	
	@Override
	//serve per selezione una riga della tabella CARRELLO utilizzando la chiave
	public synchronized Storico_bean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Storico_bean bean = new Storico_bean();//creiamo un nuovo bean, della classe Carrello
		String selectSQL = "SELECT * FROM " + Storico_DAODataSource.TABLE_NAME + " WHERE CODE = ?";
		try {
			connection = ds.getConnection();	
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				//per ogni riga salvata in "rs", crea un oggetto Carrello_bean, imposta i valori con quelli salvati in "rs" e lo restituisce; 

				bean.set_n_ordine(rs.getInt("numero ordine"));
				//int userId = rs.getInt("codice utente");
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

