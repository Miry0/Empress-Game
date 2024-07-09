package model; 

import javax.servlet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class context_listener implements ServletContextListener {

    // Metodo chiamato quando il contesto della servlet viene inizializzato
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Creazione del contesto iniziale JNDI
        InitialContext contesto_init;
        DataSource ds = null;
        ServletContext context= event.getServletContext();
        
        try {
            contesto_init = new InitialContext();
            // Lookup del DataSource
           // ds = (DataSource) contesto_init.lookup("java:/comp/env/jdbc/MyDataSource");
            
            contesto_init = new InitialContext();
            Context envCtx = (Context) contesto_init.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/MyDataSource");
            
            // Utilizzo del DataSource ottenuto
            context.setAttribute("MyDataSource", ds);
            System.out.println("DataSource inizializzato correttamente");
        } catch (NamingException e) {
            // Gestione dell'eccezione in caso di errore durante il lookup del DataSource
            System.err.println("Errore durante il lookup del DataSource: " + e.getMessage());
        }
    }

    // Metodo chiamato quando il contesto della servlet viene distrutto
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Qui si possono rilasciare le risorse se necessario
        System.out.println("Contesto distrutto");
    }
}
