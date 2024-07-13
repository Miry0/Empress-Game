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
import javax.servlet.ServletContext;
import javax.sql.DataSource;

public class Carrello_DAODataSource implements IBeanDAO<Carrello_bean> {

    private DataSource ds;

    private static final String TABLE_NAME = "CARRELLO";
    
    public Carrello_DAODataSource(DataSource ds) {
        this.ds=ds;
        if(ds==null) {
        	System.out.println("DataSource carrello nullo");
        }
        else {
        	System.out.println("DataSource carrello instanziato correttamente");
        }
        
    }
    @Override
    public synchronized void doSave(Carrello_bean carrello) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + Carrello_DAODataSource.TABLE_NAME
                + " (n_ordine, nome_utente, metodo_pagamento, totale, data_ordine, immagine) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, carrello.get_n_ordine());
            preparedStatement.setString(2, carrello.get_nome_utente());
            preparedStatement.setString(3, carrello.get_metodo_pagamento());
            preparedStatement.setFloat(4, carrello.get_totale());
            preparedStatement.setDate(5, carrello.get_data_ordine());
            preparedStatement.setBytes(6, carrello.getImmagine()); // Imposta l'immagine come array di byte

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
                carrello.set_nome_utente(rs.getString("nome_utente"));
                carrello.set_metodo_pagamento(rs.getString("metodo_pagamento"));
                carrello.set_totale(rs.getFloat("totale"));
                carrello.set_data_ordine(rs.getDate("data_ordine"));
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
                 carrello.set_nome_utente(rs.getString("nome_utente"));
                 carrello.set_metodo_pagamento(rs.getString("metodo_pagamento"));
                 carrello.set_totale(rs.getFloat("totale"));
                 carrello.set_data_ordine(rs.getDate("data_ordine"));
                 carrello.setImmagine(rs.getBytes("immagine")); // Ottiene l'immagine come array di byte
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
