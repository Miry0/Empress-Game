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

//implementiamo la classe bean che descrive la relazione tra table "LISTA_DESIDERI"E  "gIOCHI"; 
public class Sta_nella_lista_DAODataSource implements IBeanDAO<Sta_nella_lista_bean> {//implementiamo la classe bean degli utenti del catalogo che abbiamo creato

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
	private static final String TABLE_NAME = "sta_nella_lista"; //passiamo il nome della lista degli utenti su Mysql

	@Override
	public synchronized void doSave(Sta_nella_lista_bean Sta_nella_lista) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + Sta_nella_lista_DAODataSource.TABLE_NAME
				+ " (id_lista, id_utente, id_gioco) VALUES (?, ?, ?)"; //permettiamo l'inserimento di tuple nella table, attraverso una cinnessione tramite DAO+DataSource
		try {// cerchiamo di recuperare i dati salvati nel BD per i vari campi; 
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, Sta_nella_lista.get_id_lista());
			preparedStatement.setInt(2, Sta_nella_lista.get_id_utente());
			preparedStatement.setInt(2, Sta_nella_lista.get_id_gioco());
			

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

		String deleteSQL = "DELETE FROM " + Sta_nella_lista_DAODataSource.TABLE_NAME + " WHERE CODE = ?";

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
	public synchronized Collection<Sta_nella_lista_bean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		//preprata query per la selezione di tutte le righe della tabella CARRELLO; 
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
				//per ogni riga salvata in "rs", crea un oggetto Carrello_bean, imposta i valori con quelli salvati in "rs" e lo aggiunge alla collezione "carrello"; 
				Sta_nella_lista_bean bean = new Sta_nella_lista_bean();

				bean.set_id_lista(rs.getInt("codice identificativo della lista"));
				bean.set_id_utente(rs.getInt("proprietario della lista"));
				bean.set_id_gioco(rs.getInt("codice identificativo del gioco inserito"));
				
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
		return Sta_nella_lista; //restituisce la collezione costruita; 
	}
	
	@Override
	//serve per selezione una riga della tabella CARRELLO utilizzando la chiave
	public synchronized Sta_nella_lista_bean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		Sta_nella_lista_bean bean = new Sta_nella_lista_bean();//creiamo un nuovo bean, della classe Carrello
		String selectSQL = "SELECT * FROM " + Sta_nella_lista_DAODataSource.TABLE_NAME + " WHERE CODE = ?";
		try {
			connection = ds.getConnection();	
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				//per ogni riga salvata in "rs", crea un oggetto Carrello_bean, imposta i valori con quelli salvati in "rs" e lo restituisce; 

				bean.set_id_lista(rs.getInt("codice identificativo della lista"));
				bean.set_id_utente(rs.getInt("proprietario della lista"));
				bean.set_id_gioco(rs.getInt("codice identificativo del gioco inserito"));


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
