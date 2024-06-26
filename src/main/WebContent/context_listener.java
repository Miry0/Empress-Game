import javax.naming.NamingException;
import javax.servlet.*; //importiamo libreria per la gestione delle servelet

@WebListener 
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
			*/
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
