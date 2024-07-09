/*
import javax.naming.NamingException;
import javax.servlet.*; //importiamo libreria per la gestione delle servelet

@WebListener 
public class MyServletContextListener implements ServletContextListener {
	
	//definiamo un contesto iniziale JNDi per poter avere collegamento al DataSource
		public void contextInizialized(ServletContextEvent event) {
			
			ServletContext contesto= event.getServletContext();  //creiamo l'oggetto di tipo ServletContext, per poterdefinire il contesto del DataSource
			
			//creiamo l'ggetto DataSource
			DataSource sd=null; 
			
			try {
				 Context context_init= new InitialContext(); //creiamo un contesto iniziale
				 Context contesto_evento= (Context) context_init.lookup
			}
			
			InitialContext contesto_init= new InitialContext(); 
			
			DataSource ds= null; 
			try {
			     ds = (DataSource) contesto_init.lookup("java:/comp/env/jdbc/MyDataSource");
			    // usiamo il DataSource ottenuto 
			} catch (NamingException e) {
			    // gestiamo l'eccezione in caso non avvenga il collegamento al dataSource
			    System.out.println("Errore"+e.getMessage()); //nela caso venga sollevata un'eccezione, viene restituito il messaggio d'errore del tipo di eccezione specifico;  
			}

			
		}	
	
}
*/
import javax.servlet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class MyServletContextListener implements ServletContextListener {

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
            
            Context contesto_init = new InitialContext();
            Context envCtx = (Context) contesto_init.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/MyDataSource");
            
            // Utilizzo del DataSource ottenuto
            context.setAttribute("MyDataSource", ds);
            ServletContext.log("DataSource inizializzato correttamente");
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
