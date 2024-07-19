package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import javax.sql.DataSource;

public class Storico_DAODataSource implements IBeanDAO<Storico_bean> {

    private static DataSource ds;

    // Costruttore per ottenere il DataSource dal contesto dell'applicazione
    public Storico_DAODataSource(DataSource ds) {
        this.ds = ds;
        if (ds == null) {
            System.out.println("DataSource nullo");
        }
    }

    private static final String TABLE_NAME = "STORICO";

    @Override
    public synchronized void doSave(Storico_bean storico) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + TABLE_NAME + " (n_ordine, nome_utente, totale, data) VALUES (?, ?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, storico.get_n_ordine());
            preparedStatement.setString(2, storico.get_nome_utente());
            preparedStatement.setFloat(3, storico.get_totale());
            preparedStatement.setDate(4, storico.get_data());

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
    public Storico_bean doRetrieveByKey(int id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Storico_bean bean = new Storico_bean();

        String selectSQL = "SELECT * FROM " + TABLE_NAME + " WHERE n_ordine = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                bean.set_n_ordine(rs.getInt("n_ordine"));
                bean.set_nome_utente(rs.getString("nome_utente"));
                bean.set_totale(rs.getFloat("totale"));
                bean.set_data(rs.getDate("data"));
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

    @Override
    public Collection<Storico_bean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Storico_bean> storici = new LinkedList<Storico_bean>();

        String selectSQL = "SELECT * FROM " + TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Storico_bean bean = new Storico_bean();

                bean.set_n_ordine(rs.getInt("n_ordine"));
                bean.set_nome_utente(rs.getString("nome_utente"));
                bean.set_totale(rs.getFloat("totale"));
                bean.set_data(rs.getDate("data"));

                storici.add(bean);
            }

        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } finally {
                if (connection != null) connection.close();
            }
        }

        return storici;
    }
}
