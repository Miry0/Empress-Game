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


public class Recensioni_DAODataSource {

    private DataSource ds;

    private static final String TABLE_NAME = "RECENSIONI";


 // Costruttore per ottenere il DataSource dal contesto dell'applicazione
    public Recensioni_DAODataSource(DataSource ds) {
        this.ds=ds;
        if(ds==null) {
        	System.out.println("DataSource nullo");
        }
    }
        
        public synchronized void doSave(Recensioni_bean recensione) throws SQLException {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            String insertSQL = "INSERT INTO " + TABLE_NAME + " (id_recensione, nome_utente, id_gioco, testo) VALUES (?, ?, ?, ?)";

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setInt(1, recensione.get_id_recensione());
                preparedStatement.setString(2, recensione.get_nome_utente());
                preparedStatement.setFloat(3, recensione.get_id_gioco());
                preparedStatement.setString(4, recensione.get_testo());

                preparedStatement.executeUpdate();

            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } finally {
                    if (connection != null) connection.close();
                }
            }
        }
        
        
        public Recensioni_bean doRetrieveByKey(int id) throws SQLException {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            Recensioni_bean bean = new Recensioni_bean();

            String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE id_recensione = ?";

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setInt(1, id);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    bean.set_id_recensione(rs.getInt("n_ordine"));
                    bean.set_nome_utente(rs.getString("nome_utente"));
                    bean.set_id_gioco(rs.getInt("id_gioco"));
                    bean.set_testo(rs.getString("testo"));
                }

            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } finally {
                    if (connection != null) connection.close();
                }
            }

            return bean;
        }
        
        public boolean doDelete(int id) throws SQLException {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE id_recensione = ?";

            int result = 0;

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(deleteSQL);
                preparedStatement.setInt(1, id);

                result = preparedStatement.executeUpdate();

            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } finally {
                    if (connection != null) connection.close();
                }
            }

            return (result != 0);
        }
        
        public Collection<Recensioni_bean> doRetrieveAll(String order) throws SQLException {
            Connection connection = null;
            PreparedStatement preparedStatement = null;

            Collection<Recensioni_bean> recensioni = new LinkedList<Recensioni_bean>();

            String selectSQL = "SELECT * FROM " + TABLE_NAME;

            if (order != null && !order.equals("")) {
                selectSQL += " ORDER BY " + order;
            }

            try {
                connection = ds.getConnection();
                preparedStatement = connection.prepareStatement(selectSQL);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                   Recensioni_bean bean = new Recensioni_bean();

                    bean.set_id_recensione(rs.getInt("n_ordine"));
                    bean.set_nome_utente(rs.getString("nome_utente"));
                    bean.set_id_gioco(rs.getInt("id_gioco"));
                    bean.set_testo(rs.getString("testo"));

                    recensioni.add(bean);
                }

            } finally {
                try {
                    if (preparedStatement != null) preparedStatement.close();
                } finally {
                    if (connection != null) connection.close();
                }
            }

            return recensioni;
        }
       
    }
    