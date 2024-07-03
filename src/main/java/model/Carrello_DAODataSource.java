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

public class Carrello_DAODataSource implements IBeanDAO<Carrello_bean> {

    private static DataSource ds;

    private static final String TABLE_NAME = "CARRELLO";

    @Override
    public synchronized void doSave(Carrello_bean carrello) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + Carrello_DAODataSource.TABLE_NAME
                + " (n_ordine, id_utente, metodo_pagamento, totale, g_ordine, m_ordine, a_ordine, immagine) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, carrello.get_n_ordine());
            preparedStatement.setInt(2, carrello.get_id_utente());
            preparedStatement.setString(3, carrello.get_metodo_pagamento());
            preparedStatement.setFloat(4, carrello.get_totale());
            preparedStatement.setInt(5, carrello.get_g_ordine());
            preparedStatement.setInt(6, carrello.get_m_ordine());
            preparedStatement.setInt(7, carrello.get_a_ordine());
            preparedStatement.setBytes(8, carrello.getImmagine()); // Imposta l'immagine come array di byte

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
                carrello.set_id_utente(rs.getInt("id_utente"));
                carrello.set_metodo_pagamento(rs.getString("metodo_pagamento"));
                carrello.set_totale(rs.getFloat("totale"));
                carrello.set_g_ordine(rs.getInt("g_ordine"));
                carrello.set_m_ordine(rs.getInt("m_ordine"));
                carrello.set_a_ordine(rs.getInt("a_ordine"));
                carrello.setImmagine(rs.getBytes("immagine")); // Ottiene l'immagine come array di byte

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
                carrello.set_id_utente(rs.getInt("id_utente"));
                carrello.set_metodo_pagamento(rs.getString("metodo_pagamento"));
                carrello.set_totale(rs.getFloat("totale"));
                carrello.set_g_ordine(rs.getInt("g_ordine"));
                carrello.set_m_ordine(rs.getInt("m_ordine"));
                carrello.set_a_ordine(rs.getInt("a_ordine"));
                carrello.setImmagine(rs.getBytes("immagine"));
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

    // Aggiungi eventuali altri metodi necessari per gestire l'interazione con il database

}
