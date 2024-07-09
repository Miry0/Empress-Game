package model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

public class Desideri_DAODataSource implements IBeanDAO<Desideri_bean> {

    private static DataSource ds;

    private static final String TABLE_NAME = "LISTA_DESIDERI";
    
    public Desideri_DAODataSource(ServletContext context) {
        ds = (DataSource) context.getAttribute("MyDataSource");
    }
    @Override
    public synchronized void doSave(Desideri_bean Lista_desideri) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String insertSQL = "INSERT INTO " + Desideri_DAODataSource.TABLE_NAME
                + " (id_lista, nome_utente, immagine) VALUES (?, ?, ?)";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(insertSQL);
            preparedStatement.setInt(1, Lista_desideri.get_id_lista());
            preparedStatement.setString(2, Lista_desideri.get_nome_utente());

            // Aggiunta dell'immagine
            if (Lista_desideri.get_immagine() != null) {
                InputStream inputStream = new ByteArrayInputStream(Lista_desideri.get_immagine());
                preparedStatement.setBinaryStream(3, inputStream, Lista_desideri.get_immagine().length);
            } else {
                preparedStatement.setNull(3, java.sql.Types.BLOB);
            }

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

        String deleteSQL = "DELETE FROM " + Desideri_DAODataSource.TABLE_NAME + " WHERE id_lista = ?";

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
    public synchronized Collection<Desideri_bean> doRetrieveAll(String order) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Collection<Desideri_bean> Lista_desideri = new LinkedList<Desideri_bean>();

        String selectSQL = "SELECT * FROM " + Desideri_DAODataSource.TABLE_NAME;

        if (order != null && !order.equals("")) {
            selectSQL += " ORDER BY " + order;
        }

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Desideri_bean bean = new Desideri_bean();

                bean.set_id_lista(rs.getInt("id_lista"));
                bean.set_nome_utente(rs.getString("nome_utente"));

                // Recupero dell'immagine
                byte[] immagine = rs.getBytes("immagine");
                if (immagine != null && immagine.length > 0) {
                    bean.set_immagine(immagine);
                }

                Lista_desideri.add(bean);
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
        return Lista_desideri;
    }

    @Override
    public synchronized Desideri_bean doRetrieveByKey(int id_lista) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Desideri_bean bean = new Desideri_bean();

        String selectSQL = "SELECT * FROM " + Desideri_DAODataSource.TABLE_NAME + " WHERE id_lista = ?";

        try {
            connection = ds.getConnection();
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id_lista);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                bean.set_id_lista(rs.getInt("id_lista"));
                bean.set_nome_utente(rs.getString("nome_utente"));

                // Recupero dell'immagine
                byte[] immagine = rs.getBytes("immagine");
                if (immagine != null && immagine.length > 0) {
                    bean.set_immagine(immagine);
                }
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
