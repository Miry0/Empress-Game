package model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class Game_DAODataSource implements IBeanDAO<Game_bean> {

    private static DataSource ds;

    private static final String TABLE_NAME = "GIOCHI";
    
    // Query SQL per l'inserimento di un nuovo gioco
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME
            + " (id_gioco, nome, piattaforma, genere, prezzo, g_uscita, m_uscita, a_uscita, immagine) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // Query SQL per l'aggiornamento di un gioco esistente
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME
            + " SET nome = ?, piattaforma = ?, genere = ?, prezzo = ?, g_uscita = ?, m_uscita = ?, a_uscita = ?, immagine = ? WHERE id_gioco = ?";

    // Query SQL per il recupero di tutti i giochi
    private static final String SELECT_ALL_SQL = "SELECT * FROM " + TABLE_NAME;

    // Query SQL per il recupero di un gioco tramite id_gioco
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_gioco = ?";

    // Query SQL per l'eliminazione di un gioco tramite id_gioco
    private static final String DELETE_SQL = "DELETE FROM " + TABLE_NAME + " WHERE id_gioco = ?";;


 // Costruttore per ottenere il DataSource dal contesto dell'applicazione
    public Game_DAODataSource(ServletContext context) {
        ds = (DataSource) context.getAttribute("MyDataSource");
    }

    @Override
    public synchronized void doSave(Game_bean game) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_SQL);

            preparedStatement.setInt(1, game.get_id_gioco());
            preparedStatement.setString(2, game.get_nome());
            preparedStatement.setString(3, game.get_piattaforma());
            preparedStatement.setString(4, game.get_genere());
            preparedStatement.setFloat(5, game.get_prezzo());
            preparedStatement.setInt(6, game.get_g_uscita());
            preparedStatement.setInt(7, game.get_m_uscita());
            preparedStatement.setInt(8, game.get_a_uscita());
            preparedStatement.setBytes(9, game.getImmagine());

            preparedStatement.executeUpdate();

        } finally {
            closeResources(preparedStatement, connection);
        }
    }

    @Override
    public synchronized boolean doDelete(int id_gioco) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int result = 0;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_SQL);
            preparedStatement.setInt(1, id_gioco);

            result = preparedStatement.executeUpdate();

        } finally {
            closeResources(preparedStatement, connection);
        }

        return (result != 0);
    }

    @Override
    public synchronized Collection<Game_bean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Collection<Game_bean> games = new LinkedList<>();

        String selectSQL = SELECT_ALL_SQL;
        if (order != null && !order.isEmpty()) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Game_bean game = new Game_bean();
                game.set_id_gioco(resultSet.getInt("id_gioco"));
                game.set_nome(resultSet.getString("nome"));
                game.set_piattaforma(resultSet.getString("piattaforma"));
                game.set_genere(resultSet.getString("genere"));
                game.set_prezzo(resultSet.getFloat("prezzo"));
                game.set_g_uscita(resultSet.getInt("g_uscita"));
                game.set_m_uscita(resultSet.getInt("m_uscita"));
                game.set_a_uscita(resultSet.getInt("a_uscita"));
                game.setImmagine(resultSet.getBytes("immagine"));

                games.add(game);
            }

        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return games;
    }

    @Override
    public synchronized Game_bean doRetrieveByKey(int id_gioco) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Game_bean game = null;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_BY_ID_SQL);
            preparedStatement.setInt(1, id_gioco);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                game = new Game_bean();
                game.set_id_gioco(resultSet.getInt("id_gioco"));
                game.set_nome(resultSet.getString("nome"));
                game.set_piattaforma(resultSet.getString("piattaforma"));
                game.set_genere(resultSet.getString("genere"));
                game.set_prezzo(resultSet.getFloat("prezzo"));
                game.set_g_uscita(resultSet.getInt("g_uscita"));
                game.set_m_uscita(resultSet.getInt("m_uscita"));
                game.set_a_uscita(resultSet.getInt("a_uscita"));
                game.setImmagine(resultSet.getBytes("immagine"));
            }

        } finally {
            closeResources(resultSet, preparedStatement, connection);
        }

        return game;
    }

    public synchronized void update(Game_bean game) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_SQL);

            preparedStatement.setString(1, game.get_nome());
            preparedStatement.setString(2, game.get_piattaforma());
            preparedStatement.setString(3, game.get_genere());
            preparedStatement.setFloat(4, game.get_prezzo());
            preparedStatement.setInt(5, game.get_g_uscita());
            preparedStatement.setInt(6, game.get_m_uscita());
            preparedStatement.setInt(7, game.get_a_uscita());
            preparedStatement.setBytes(8, game.getImmagine());
            preparedStatement.setInt(9, game.get_id_gioco());

            preparedStatement.executeUpdate();

        } finally {
            closeResources(preparedStatement, connection);
        }
    }

    // Metodo per chiudere le risorse del database in modo sicuro
    private void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (Exception e) {
                    // Gestione dell'eccezione o log dell'errore
                }
            }
        }
    }

    
    public List<Game_bean> searchGamesByName(String nome) throws SQLException { //utile per la ricerca di un gioco tramite il nome
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<Game_bean> games = new LinkedList<>();
        String searchSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome LIKE ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(searchSQL);
            preparedStatement.setString(1, "%" + nome + "%"); // Per cercare il nome parziale in ogni parte della stringa nome

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { 
                Game_bean game = new Game_bean();
                game.set_id_gioco(resultSet.getInt("id_gioco"));
                game.set_nome(resultSet.getString("nome"));;
                game.set_piattaforma(resultSet.getString("piattaforma"));
                game.set_genere(resultSet.getString("genere"));
                game.set_prezzo(resultSet.getFloat("prezzo"));
                game.set_g_uscita(resultSet.getInt("g_uscita"));
                game.set_m_uscita(resultSet.getInt("m_uscita"));
                game.set_a_uscita(resultSet.getInt("a_uscita"));
                game.setImmagine(resultSet.getBytes("immagine"));

                games.add(game); //aggiunge i giochi che rispettano il parametro di ricerca ad una lista da mostrare al client
            }
        } finally {
            //closeResources(resultSet, preparedStatement, connection); //chiude le risorse
        }

        return games; //restituisce la lista
    }
}


