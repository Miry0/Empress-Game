package control;


import model.Sta_nella_lista_bean;


import model.Utenti_DAODataSource;
import model.Sta_nella_lista_DAODataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import model.Sta_nella_lista_bean;
import model.Sta_nella_lista_DAODataSource;
import model.Game_bean;
import model.Game_DAODataSource;
import model.Desideri_bean;
import model.Desideri_DAODataSource;
import model.Utenti_bean;
import model.Utenti_DAODataSource;

//@WebServlet("/wishlist")
public class lista_desideri_servlet extends HttpServlet {

	private Sta_nella_lista_DAODataSource staNellaListaDAO;
	private Desideri_DAODataSource desideriDAO;
	private Game_DAODataSource gameDAO;
	private Utenti_DAODataSource utentiDAO;


	 public void init(ServletConfig cfg) throws ServletException {
	        super.init(cfg);
	        // Inizializzazione del DAO per interagire con il database dei giochi
	       // gameDAO = new Game_DAODataSource(getServletContext());
	    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
    	
    	String nomeUtente = request.getParameter("nome_utente");

        try {
            viewWishlist(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        staNellaListaDAO = new Sta_nella_lista_DAODataSource(ds);
        desideriDAO = new Desideri_DAODataSource(ds);
        gameDAO=new Game_DAODataSource(ds);
        utentiDAO= new Utenti_DAODataSource(ds);
        
    	String action = request.getParameter("lista_desideri"); //recuperiamo l'azione da compiere con i dati passati dalla form
       System.out.println("azione della lista"+ action);
        
        if (action == null) {
            action = "view";
        }

        try {
        	
            switch (action) {
                case "add":
                    addToWishlist(request, response);
                    break;
                case "remove":
                    removeFromWishlist(request, response);
                    break;
                default:
                    viewWishlist(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    // Metodo per aggiungere un prodotto alla lista desideri
    private void addToWishlist(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id_gioco =  Integer.parseInt(request.getParameter("gioco_aggiungi_lista")); //recuperiamo l'id del gioco che l'utente vuole inserire
        String nome_utente = request.getParameter("nome_utente_lista"); //recuperiamo il nome_utente 
        Desideri_bean Lista= (Desideri_bean) desideriDAO.doRetrieveByUserName(nome_utente); 
        
        if((desideriDAO.isGameInWishlist(id_gioco, nome_utente))==true) {
        	
        	String messaggioErrore = "Il gioco è già presente nella lista desideri.";
        	
      	  Game_bean gioco = null;
            try {
                gioco = gameDAO.doRetrieveByKey(id_gioco);
            } catch (SQLException e) {
                e.printStackTrace(); // Gestisci l'eccezione in base alle tue esigenze
            }
            request.setAttribute("gioco", gioco); // Passa l'oggetto gioco alla JSP
            
            
          request.setAttribute("messaggioErrore", messaggioErrore);
          RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Dettaglio_prodotto.jsp");
          dispatcher.forward(request, response);      
        }
        else { 
        if (Lista != null) {
            Sta_nella_lista_bean staNellaLista = new Sta_nella_lista_bean();
            
            staNellaLista.set_id_lista(desideriDAO.getListaIdByUser(nome_utente));
            staNellaLista.set_nome_utente(nome_utente);
            staNellaLista.set_id_gioco(id_gioco);

            staNellaListaDAO.doSave(staNellaLista); // Salva il bean nella lista dei desideri
        }
        
        
        viewWishlist(request, response); // Visualizza nuovamente la lista dei desideri
        }
    }
    
    // Metodo per rimuovere un prodotto dalla lista desideri
    private void removeFromWishlist(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int idGioco =Integer.parseInt (request.getParameter("id_gioco"));
        String nomeUtente = request.getParameter("nome_utente");
       // int idLista = Integer.parseInt(request.getParameter("id_lista"));
    	Collection<Sta_nella_lista_bean> wishlist=null; 
    	Collection<Game_bean > listaGiochi = gameDAO.Mostra_tutto(); // Ottieni la lista dei desideri dall'utente
        
        if (idGioco != -1) {
         wishlist = staNellaListaDAO.aggiorna_lista( nomeUtente, idGioco); // Elimina il gioco dalla lista dei desideri
        }
        
        request.setAttribute("listaGiochi", listaGiochi); 
        request.setAttribute("wishlist", wishlist);
       // viewWishlist(request, response); // Visualizza nuovamente la lista dei desideri
        request.getRequestDispatcher("/scripts/wishlist.jsp").forward(request, response); // Inoltra alla pagina JSP per la visualizzazione
    }

    // Metodo per visualizzare la lista dei desideri di un utente
    private void viewWishlist(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
    	String nome_utente = request.getParameter("nome_utente_lista"); //recuperiamo il nome_utente 
    	
    	Utenti_bean Utente=(Utenti_bean) utentiDAO.doRetrieveByKey(nome_utente); //recuperiamo l'utente tramite il suo nome_utente
    	String tipoUtente=Utente.get_tipo(); 
        Collection<Sta_nella_lista_bean> wishlist = staNellaListaDAO.getWishlistByUser(nome_utente); // Ottieni la lista dei desideri dall'utente
        Collection<Game_bean > listaGiochi = gameDAO.Mostra_tutto(); // Ottieni la lista dei desideri dall'utente
        
        request.setAttribute("tipoUtente", tipoUtente);
        request.setAttribute("listaGiochi", listaGiochi);
        request.setAttribute("wishlist", wishlist); // Imposta l'attributo nella richiesta
        request.getRequestDispatcher("/scripts/wishlist.jsp").forward(request, response); // Inoltra alla pagina JSP per la visualizzazione
    }
}
