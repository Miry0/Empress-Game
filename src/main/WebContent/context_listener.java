import javax.naming.NamingException;
import javax.servlet.*; //importiamo libreria per la gestione delle servelet

@WebListener 
/*
public class MyServletContextListener implements ServletContextListener {
	
	//definiamo un contesto iniziale JNDi per poter avere collegamento al DataSource
		public void contextInizialized(ServletContextEvent event) {
			/*
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
			     ds = (DataSource) contesto_init.lookup("java:/comp/env/jdbc/Empress_DB");
			    // usiamo il DataSource ottenuto 
			} catch (NamingException e) {
			    // gestiamo l'eccezione in caso non avvenga il collegamento al dataSource
			    System.out.println("Errore"+e.getMessage()); //nela caso venga sollevata un'eccezione, viene restituito il messaggio d'errore del tipo di eccezione specifico;  
			}

			
		}
	
*/	
	
public class MyServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        // Ottieni il contesto del Servlet
        ServletContext servletContext = event.getServletContext();

        // Creazione del DataSource
        DataSource dataSource = null;
        try {
            // Ottieni il contesto iniziale
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            // Cerca il DataSource nel contesto JNDI
            dataSource = (DataSource) envContext.lookup("jdbc/MyDataSource");

            // Usa il DataSource ottenuto
            servletContext.setAttribute("myDataSource", dataSource); // Esempio di settaggio di attributo nel contesto del servlet
        } catch (NamingException e) {
            // Gestione dell'eccezione nel caso in cui non sia possibile trovare il DataSource
            System.out.println("Errore nella configurazione del DataSource: " + e.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        // Eventuale codice di rilascio delle risorse alla distruzione del contesto
    }
}
	
	
	
	
	
	
	
	
}
