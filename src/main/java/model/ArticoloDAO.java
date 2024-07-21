package model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;

public class ArticoloDAO implements IBeanDAO<ArticoloBean> {

    private static DataSource ds;

    // Costruttore per ottenere il DataSource dal contesto dell'applicazione
    public ArticoloDAO(DataSource ds) {
    	
        this.ds = ds;
        if (ds == null) {
            System.out.println("DataSource nullo");
        }
    }

    private static final String TABLE_NAME = "ARTICOLI";
    
    @Override
    public synchronized void doSave(ArticoloBean articolo) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME + " (n_ordine, id_gioco, quantita) VALUES (?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, articolo.getnOrdine());
            preparedStatement.setInt(2, articolo.get_id_gioco());
            preparedStatement.setInt(3, articolo.get_quantita());

            preparedStatement.executeUpdate();

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }
    }
    
    @Override
    public boolean doDelete(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE n_ordine = ?";

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
    
    @Override
    public ArticoloBean doRetrieveByKey(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        ArticoloBean bean = new ArticoloBean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE n_ordine = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.setnOrdine(rs.getInt("n_ordine"));
                bean.set_id_gioco(rs.getInt("idGioco"));
                bean.set_quantita(rs.getInt("quantita"));
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
    
    
    public Collection<ArticoloBean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Collection<ArticoloBean> articoli = new LinkedList<ArticoloBean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;
        
        if (order != null && !order.equals(""))
            selectSQL += " ORDER BY " + order;

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ArticoloBean bean = new ArticoloBean();
                bean.setnOrdine(rs.getInt("n_ordine"));
                bean.set_id_gioco(rs.getInt("id_gioco"));
                bean.set_quantita(rs.getInt("quantita"));
                articoli.add(bean);
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }

        return articoli;
    }

}