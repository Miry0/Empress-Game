package model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;

import javax.sql.DataSource;

/**
 * Questa classe implementa l'interfaccia IBeanDAO per gestire l'accesso ai dati del carrello nel database utilizzando DataSource.
 */
public class Carrello_DAODataSource implements IBeanDAO<Carrello_bean> {

    private DataSource ds; // DataSource per la connessione al database
    private static final String TABLE_NAME = "CARRELLO"; // Nome della tabella nel database

    /**
     * Costruttore della classe Carrello_DAODataSource.
     * 
     * @param ds Il DataSource per la connessione al database
     */
    public Carrello_DAODataSource(DataSource ds) {
        this.ds = ds;
        if (ds == null) {
            System.out.println("DataSource carrello nullo");
        } else {
            System.out.println("DataSource carrello instanziato correttamente");
        }
    }

    /**
     * Metodo per salvare un oggetto Carrello_bean nel database.
     * 
     * @param carrello L'oggetto Carrello_bean da salvare
     * @throws SQLException Se si verifica un errore SQL durante l'operazione di salvataggio
     */
    @Override
    public synchronized void doSave(Carrello_bean carrello) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + Carrello_DAODataSource.TABLE_NAME
                + " (n_ordine, nome_utente, metodo_pagamento, totale, data_ordine, immagine, gamesList) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, carrello.get_n_ordine());
            preparedStatement.setString(2, carrello.get_nome_utente());
            preparedStatement.setString(3, carrello.get_metodo_pagamento());
            preparedStatement.setFloat(4, carrello.get_totale());
            preparedStatement.setDate(5, carrello.get_data_ordine());
            preparedStatement.setBytes(6, carrello.getImmagine()); // Imposta l'immagine come array di byte

            // Converti gamesList in stringa
            String gamesListStr = convertArrayListToString(carrello.getGamesList());
            preparedStatement.setString(7, gamesListStr);

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

    /**
     * Metodo per eliminare un record dal database basato sul numero d'ordine.
     * 
     * @param code Il numero d'ordine da utilizzare per l'eliminazione
     * @return true se l'eliminazione ha successo, false altrimenti
     * @throws SQLException Se si verifica un errore SQL durante l'operazione di eliminazione
     */
    @Override
    public synchronized boolean doDelete(int code) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + Carrello_DAODataSource.TABLE_NAME + " WHERE n_ordine = ?";

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

    /**
     * Metodo per recuperare tutti i record dal database e restituirli come una collezione di Carrello_bean.
     * 
     * @param order La clausola ORDER BY per ordinare i risultati (opzionale)
     * @return Una collezione di Carrello_bean contenente tutti i record nel database
     * @throws SQLException Se si verifica un errore SQL durante l'operazione di recupero
     */
    @Override
    public synchronized Collection<Carrello_bean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Carrello_bean> carrelli = new LinkedList<Carrello_bean>();

        String selectSQL = "SELECT * FROM " + Carrello_DAODataSource.TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Carrello_bean carrello = new Carrello_bean();

                carrello.set_n_ordine(rs.getInt("n_ordine"));
                carrello.set_nome_utente(rs.getString("nome_utente"));
                carrello.set_metodo_pagamento(rs.getString("metodo_pagamento"));
                carrello.set_totale(rs.getFloat("totale"));
                carrello.set_data_ordine(rs.getDate("data_ordine"));
                carrello.setImmagine(rs.getBytes("immagine")); // Ottiene l'immagine come array di byte

                // Recupera gamesList come stringa e convertila in ArrayList
                String gamesListStr = rs.getString("gamesList");
                ArrayList<Integer> gamesList = convertStringToArrayList(gamesListStr);
                carrello.setGameList(gamesList);

                carrelli.add(carrello);
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
        return carrelli;
    }

    /**
     * Metodo per recuperare un record dal database basato sul numero d'ordine.
     * 
     * @param n_ordine Il numero d'ordine da utilizzare per il recupero
     * @return L'oggetto Carrello_bean corrispondente al numero d'ordine specificato
     * @throws SQLException Se si verifica un errore SQL durante l'operazione di recupero
     */
    @Override
    public synchronized Carrello_bean doRetrieveByKey(int n_ordine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Carrello_bean carrello = new Carrello_bean();

        String selectSQL = "SELECT * FROM " + Carrello_DAODataSource.TABLE_NAME + " WHERE n_ordine = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, n_ordine);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                carrello.set_n_ordine(rs.getInt("n_ordine"));
                carrello.set_nome_utente(rs.getString("nome_utente"));
                carrello.set_metodo_pagamento(rs.getString("metodo_pagamento"));
                carrello.set_totale(rs.getFloat("totale"));
                carrello.set_data_ordine(rs.getDate("data_ordine"));
                carrello.setImmagine(rs.getBytes("immagine")); // Ottiene l'immagine come array di byte

                // Recupera gamesList come stringa e convertila in ArrayList
                String gamesListStr = rs.getString("gamesList");
                ArrayList<Integer> gamesList = convertStringToArrayList(gamesListStr);
                carrello.setGameList(gamesList);
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
        return carrello;
    }

    /**
     * Metodo ausiliario per convertire un ArrayList di Integer in una stringa delimitata.
     * 
     * @param list L'ArrayList di Integer da convertire
     * @return Una stringa delimitata contenente gli elementi dell'ArrayList
     */
    private String convertArrayListToString(ArrayList<Integer> list) {
        StringBuilder sb = new StringBuilder();
        for (Integer id : list) {
            sb.append(id).append(",");
        }
        // Rimuovi l'ultimo delimitatore
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Metodo ausiliario per convertire una stringa delimitata in un ArrayList di Integer.
     * 
     * @param str La stringa delimitata da convertire
     * @return Un ArrayList di Integer contenente gli elementi della stringa
     */
    private ArrayList<Integer> convertStringToArrayList(String str) {
        ArrayList<Integer> list = new ArrayList<>();
        if (str != null && !str.isEmpty()) {
            String[] items = str.split(",");
            for (String item : items) {
                list.add(Integer.parseInt(item));
            }
        }
        return list;
    }

    /**
     * Metodo per recuperare la lista di ID dei giochi associata a un determinato numero d'ordine.
     * 
     * @param n_ordine Il numero d'ordine di cui recuperare la lista dei giochi
     * @return Un ArrayList di Integer contenente gli ID dei giochi nel carrello
     * @throws SQLException Se si verifica un errore SQL durante l'operazione di recupero
     */
    public synchronized ArrayList<Integer> recuperaGamesList(int n_ordine) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Integer> gamesList = new ArrayList<>();

        String selectSQL = "SELECT gamesList FROM " + Carrello_DAODataSource.TABLE_NAME + " WHERE n_ordine = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, n_ordine);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Recupera gamesList come stringa e convertila in ArrayList
                String gamesListStr = rs.getString("gamesList");
                gamesList = convertStringToArrayList(gamesListStr);
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
        return gamesList;
    }
    
}
