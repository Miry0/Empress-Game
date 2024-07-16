package model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;

// Implementazione del DAO per la tabella "sta_nella_lista"
public class Sta_nella_lista_DAODataSource implements IBeanDAO<Sta_nella_lista_bean> {

    private static DataSource ds;

    public Sta_nella_lista_DAODataSource(DataSource ds) {
        this.ds=ds;
        if(ds==null) {
        	System.out.println("DataSource nullo");
        }
    }

    private static final String TABLE_NAME = "sta_nella_lista";

    @Override
    public synchronized void doSave(Sta_nella_lista_bean staNellaLista) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME + " (id_lista, nome_utente, id_gioco) VALUES (?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, staNellaLista.get_id_lista());
            preparedStatement.setString(2, staNellaLista.get_nome_utente());
            preparedStatement.setInt(3, staNellaLista.get_id_gioco());

            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }

    public synchronized boolean deleteByListIdAndGameId(int idLista, String nomeUtente, int idGioco) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_lista = ? AND nome_utente = ? AND id_gioco = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setInt(1, idLista);
            preparedStatement.setString(2, nomeUtente);
            preparedStatement.setInt(3, idGioco);

            result = preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return (result != 0);
    }

    @Override
    public synchronized Collection<Sta_nella_lista_bean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<Sta_nella_lista_bean> listaDesideri = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

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

                listaDesideri.add(bean);
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return listaDesideri;
    }

    @Override
    public synchronized Sta_nella_lista_bean doRetrieveByKey(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Sta_nella_lista_bean bean = new Sta_nella_lista_bean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_lista = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, code);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean.set_id_lista(rs.getInt("id_lista"));
                bean.set_nome_utente(rs.getString("nome_utente"));
                bean.set_id_gioco(rs.getInt("id_gioco"));
      
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return bean;
    }


    // Metodo per ottenere la lista dei desideri di un utente
    public synchronized Collection<Sta_nella_lista_bean> getWishlistByUser(String nomeUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<Sta_nella_lista_bean> wishlist = new LinkedList<>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_utente = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, nomeUtente);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Sta_nella_lista_bean bean = new Sta_nella_lista_bean();
                bean.set_id_lista(rs.getInt("id_lista"));
                bean.set_nome_utente(rs.getString("nome_utente"));
                bean.set_id_gioco(rs.getInt("id_gioco"));

                wishlist.add(bean);
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return wishlist;
    }
    
    public synchronized Integer getListaIdByUser(String nomeUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Integer idLista = null;

        String selectSQL = "SELECT id_lista FROM " + TABLE_NAME + " WHERE nome_utente = ? LIMIT 1";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, nomeUtente);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                idLista = rs.getInt("id_lista");
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return idLista;
    }


    /*
    // Metodo per recuperare l'immagine associata al gioco
    public synchronized byte[] retrieveGameImage(int gameId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        byte[] immagine = null;

        String selectSQL = "SELECT immagine FROM giochi WHERE id_gioco = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, gameId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                immagine = rs.getBytes("immagine");
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return immagine;
    }
*/
    
    // Metodo per ottenere l'ID del gioco dal nome
    public synchronized int getGameIdFromName(String gameName) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int idGioco = -1;

        String selectSQL = "SELECT id_gioco FROM giochi WHERE nome = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, gameName);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                idGioco = rs.getInt("id_gioco");
            }
        } finally {
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }

        return idGioco;
    }
    
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + Sta_nella_lista_DAODataSource.TABLE_NAME + " WHERE id_lista = ?";

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
    
 // Metodo per ottenere la lista dei desideri di un utente ed eliminare un gioco specifico
    public synchronized Collection<Sta_nella_lista_bean> aggiorna_lista(String nomeUtente, int idGiocoDaEliminare) throws SQLException {
        Connection connection = null;
        PreparedStatement deleteStatement = null;
        PreparedStatement selectStatement = null;
        Collection<Sta_nella_lista_bean> wishlist = new LinkedList<>();

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE nome_utente = ? AND id_gioco = ?";
        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_utente = ?";

        try {
            connection = ds.getConnection();
            
            // Esegui la query di eliminazione
            deleteStatement = connection.prepareStatement(deleteSQL);
            deleteStatement.setString(1, nomeUtente);
            deleteStatement.setInt(2, idGiocoDaEliminare);
            deleteStatement.executeUpdate();
            
            // Esegui la query per ottenere la lista aggiornata dei desideri
            selectStatement = connection.prepareStatement(selectSQL);
            selectStatement.setString(1, nomeUtente);

            ResultSet rs = selectStatement.executeQuery();

            while (rs.next()) {
                Sta_nella_lista_bean bean = new Sta_nella_lista_bean();
                bean.set_id_lista(rs.getInt("id_lista"));
                bean.set_nome_utente(rs.getString("nome_utente"));
                bean.set_id_gioco(rs.getInt("id_gioco"));

                wishlist.add(bean);
            }
        } finally {
            if (deleteStatement != null) deleteStatement.close();
            if (selectStatement != null) selectStatement.close();
            if (connection != null) connection.close();
        }

        return wishlist;
    }

}
