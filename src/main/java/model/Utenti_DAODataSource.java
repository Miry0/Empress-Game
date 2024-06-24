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

public class Utenti_DAODataSource {//non implementiamo l'interfaccia dei DAo, perchè abbiamo dei metodi che prendono in ingresso dei parametri diversi
    
    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/storage");

        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private static final String TABLE_NAME = "UTENTI";

   // @Override
    public synchronized void doSave(Utenti_bean utente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME
                + " (nome_utente, nome, cognome, _password, tipo, g_nascita, m_nascita, a_nascita) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setString(1, utente.get_nome_utente());
            preparedStatement.setString(2, utente.get_nome());
            preparedStatement.setString(3, utente.get_cognome());
            preparedStatement.setString(4, utente.get_password());
            preparedStatement.setString(5, utente.get_tipo());
            preparedStatement.setInt(6, utente.get_g_nascita());
            preparedStatement.setInt(7, utente.get_m_nascita());
            preparedStatement.setInt(8, utente.get_a_nascita());

            preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    //@Override
    public synchronized boolean doDelete(String nomeUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        int result = 0;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE nome_utente = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(deleteSQL);
            preparedStatement.setString(1, nomeUtente);

            result = preparedStatement.executeUpdate();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return (result != 0);
    }

   // @Override
    public synchronized Collection<Utenti_bean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Utenti_bean> utenti = new LinkedList<Utenti_bean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Utenti_bean bean = new Utenti_bean();

                bean.set_nome_utente(rs.getString("nome_utente"));
                bean.set_nome(rs.getString("nome"));
                bean.set_cognome(rs.getString("cognome"));
                bean.set_password(rs.getString("_password"));
                bean.set_tipo(rs.getString("tipo"));
                bean.set_g_nascita(rs.getInt("g_nascita"));
                bean.set_m_nascita(rs.getInt("m_nascita"));
                bean.set_a_nascita(rs.getInt("a_nascita"));

                utenti.add(bean);
            }
            rs.close();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return utenti;
    }

   // @Override
    public synchronized Utenti_bean doRetrieveByKey(String nomeUtente) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Utenti_bean bean = new Utenti_bean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE nome_utente = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, nomeUtente);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.set_nome_utente(rs.getString("nome_utente"));
                bean.set_nome(rs.getString("nome"));
                bean.set_cognome(rs.getString("cognome"));
                bean.set_password(rs.getString("_password"));
                bean.set_tipo(rs.getString("tipo"));
                bean.set_g_nascita(rs.getInt("g_nascita"));
                bean.set_m_nascita(rs.getInt("m_nascita"));
                bean.set_a_nascita(rs.getInt("a_nascita"));
            }
            rs.close();

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return bean;
    }
    /*Recupera l'utente dal database utilizzando il nome utente.
    Se l'utente esiste, calcola l'hash della password inserita e confronta con quella memorizzata nel database.
    Restituisce l'utente se le credenziali sono corrette, altrimenti restituisce null.*/
    public synchronized Utenti_bean verificaCredenziali(String username, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Utenti_bean bean = null;
        String selectSQL = "SELECT * FROM " + Utenti_DAODataSource.TABLE_NAME + " WHERE nome_utente = ? AND _password = ?";
        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                bean = new Utenti_bean();
                bean.set_nome_utente(rs.getString("nome_utente"));
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
