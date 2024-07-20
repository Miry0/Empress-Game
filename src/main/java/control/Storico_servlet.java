package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Carrello;
import model.Game_DAODataSource;
import model.Game_bean;
import model.Storico_bean;
import model.Storico_DAODataSource;

//@WebServlet("/Storico_servlet")
public class Storico_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Game_DAODataSource gameDAO;
    private Storico_DAODataSource storicoDAO;


    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action=request.getParameter("confema_ordine"); 
        
        if("conferma".equals(action)) {
        	conferma_ordine(request, response);
        }
        
    }
    
    private void conferma_ordine(HttpServletRequest request, HttpServletResponse response) {
    	 DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
         gameDAO = new Game_DAODataSource(ds);
         storicoDAO = new Storico_DAODataSource(ds);
         
         Collection<Storico_bean> listOrder = new ArrayList<>();
                  
         
        String nome_utente=request.getParameter("nome_utente"); 
    	Carrello carrello = (Carrello) request.getAttribute("carrello"); //reucpero dei dati del carrello dalla richiesta di conferma dell'ordine
    	float totale = Float.parseFloat(request.getParameter("totale"));
    	Date data= new Date(); //prende la data del server; 
    	
    	Storico_bean storico = new Storico_bean();
    	
    	storico.set_nome_utente(nome_utente);
    	storico.set_totale(totale);
    	storico.set_data(new java.sql.Date(data.getTime()));	//conversione da util.date a sql.date
    	
    	  // Salva i dati nel database
        try {
            storicoDAO.doSave(storico);
            listOrder = storicoDAO.doRetrieveAll("n_ordine DESC");
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestisci l'errore, ad esempio reindirizzando l'utente a una pagina di errore
           // response.sendRedirect("errore.jsp");
            return;
        }
        
        Storico_bean lastOrder = (Storico_bean) listOrder.toArray()[0];
        int n_ordine = lastOrder.get_n_ordine();
        
        for(int n = 0; n < carrello.getCarrelloLenght(); n++) {
        	ArticoloBean articolo = new ArticoloBean();
        	int idGioco = carrello.getGiocoByIndex(n);
        	
        	articolo.setNOrdine(n_ordine);
        	articolo.setIdGioco(idGioco);
        	articolo.setQuantita(carrello.getQuant(idGioco));
        	
        	try {
                articoloDAO.doSave(articolo);
            } catch (SQLException e) {
                e.printStackTrace();
                // Gestisci l'errore, ad esempio reindirizzando l'utente a una pagina di errore
               // response.sendRedirect("errore.jsp");
                return;
            }   
        }
        
        // Dopo aver salvato con successo, puoi reindirizzare l'utente a una pagina di conferma
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Storico.jsp");
        dispatcher.forward(request, response);
        
        
    	
    }
}