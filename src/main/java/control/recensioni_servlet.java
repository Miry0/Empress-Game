package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
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

import model.Utenti_bean;
import model.Utenti_DAODataSource;
import model.Game_DAODataSource;
import model.Game_bean;
import model.Recensioni_DAODataSource;
import model.Recensioni_bean;


public class recensioni_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Game_DAODataSource gameDAO;
    private Utenti_DAODataSource UtentiDAO;
    private Recensioni_DAODataSource recensioniDAO;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
        // gameDAO = new Game_DAODataSource(getServletContext());
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        gameDAO = new Game_DAODataSource(ds);
        UtentiDAO = new Utenti_DAODataSource(ds);
        recensioniDAO = new Recensioni_DAODataSource(ds);
        
        String action=request.getParameter("recensione"); //recuperiamo l'azione da fare 
        
        if (action != null) {
            switch (action) {
                case "add_recensione":
                    aggiungiRecensione(request, response);
                    break;
                default:
                	mostraRecensioni(request, response);
                    break;
            }
        } else {
        	mostraRecensioni(request, response);
        }
    }

    private void mostraRecensioni(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 int idGioco = Integer.parseInt(request.getParameter("gioco_aggiungi_recensione"));
    	 
    	try {
    	Collection<Recensioni_bean> recensioni =(Collection<Recensioni_bean>) recensioniDAO.doRetrieveByIdGame(idGioco); //reucuperiamo tutte le recensioni
    	
    	request.setAttribute("recensioni", recensioni);// settiamo l'attributo recensioni da recuperare nella jsp dei dettagli giochi
    	//RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Dettagli_prodotto.jsp");
        //dispatcher.forward(request, response);
    	} catch (SQLException e) {
        e.printStackTrace();
        //request.setAttribute("error", "Errore durante il recupero dei giochi."); //mostra il catalogo, quando un utente visita la pagina index.jsp
       // request.getRequestDispatcher("error.jsp").forward(request, response);
    } 
 
       
    }

    private void aggiungiRecensione(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        int idGioco = Integer.parseInt(request.getParameter("gioco_aggiungi_recensione"));
        String nome_utente=request.getParameter("nome_utente_recensione");
        String testo=request.getParameter("review");
              	
        // Crea un nuovo recensione con i dati ricevuti 
        Recensioni_bean nuovaRecensione = new Recensioni_bean();
        nuovaRecensione.set_nome_utente(nome_utente);
        nuovaRecensione.set_id_gioco(idGioco);
        nuovaRecensione.set_testo(testo);
        
        System.out.println("nuovaRecensione"+ nuovaRecensione);
        
        try {
        	recensioniDAO.doSave(nuovaRecensione);
            System.out.println("recensioniDAO"+ recensioniDAO);
            
          RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Successo_recensione.jsp");
          dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            // Gestione dell'eccezione SQL
            e.printStackTrace();
            //request.getRequestDispatcher("index.jsp").forward(request, response);
            	// Reindirizza a pagina di errore
        }
        
       
    }


    public void destroy() {
        super.destroy();
    }
}
