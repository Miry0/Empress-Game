package control; 

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.mysql.cj.Session;

import model.Carrello_DAODataSource;
import model.Carrello_bean;
import model.Game_DAODataSource;
import model.Game_bean;

import model.Carrello;

//@WebServlet("/CarrelloServlet")
public class Carrello_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Carrello_DAODataSource carrelloDAO;
    private Game_DAODataSource gameDAO;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
       // gameDAO = new Game_DAODataSource(getServletContext());
    }

 //gestiamo il carrello direttamente con il metodo dopost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
        carrelloDAO = new Carrello_DAODataSource(ds);
        gameDAO = new Game_DAODataSource(ds);
        
        HttpSession session = request.getSession();
        Carrello carrello = (Carrello) session.getAttribute("carrello");
         
    	String action = request.getParameter("azione_carrello");

        if (action != null) {
            switch (action) {
                case "aggiungi":
                    aggiungiElemento(request, response);
                    break;
                case "elimina":
                    eliminaElemento(request, response);
                    break;
                default:
                	 mostraCarrello(request, response, carrello);
                	//RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
                    //dispatcher.forward(request, response);                    
                    break;
            }
        } else {
            mostraCarrello(request, response, carrello);
        }
    }
    

    private void mostraCarrello(HttpServletRequest request, HttpServletResponse response, Carrello carrello)
            throws ServletException, IOException {
		Collection<Game_bean> catalogo = new LinkedList<>();
    	
    	for(int i = 0; i < carrello.getCarrelloLenght(); i++){
    		int idGioco = carrello.getGiocoByIndex(i);
    		Game_bean gioco = null;
    		
    		try {
				gioco = gameDAO.doRetrieveByKey(idGioco);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		if(gioco != null && !gioco.isEmpty())
    			catalogo.add(gioco);
    	}
    	
    	HttpSession session = request.getSession();
    	session.setAttribute("catalogo", catalogo);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Gestione_carrello.jsp"); //reindirizziamo alla jsp
        dispatcher.forward(request, response);
    	
        /*try {
            Collection<Carrello_bean> carrello = carrelloDAO.doRetrieveAll(null); //recuperiamo il contenuto del carrello
            request.setAttribute("carrello", carrello); //settiamo l'attributo carrello che verrà recuperato nella jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp"); //reindirizziamo alla jsp
            dispatcher.forward(request, response);
            } catch (SQLException e) {
            throw new ServletException(e);
        }*/
    }

    private void aggiungiElemento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//Retrieving the session
    	HttpSession session = request.getSession();
    	int idGioco=Integer.parseInt(request.getParameter("aggiungi_carrello")); //recuperiamo l'id del gioco passato nella richiesta alla servlet
    	System.out.println("idGioco"+idGioco); 
    	
        // Recupera i parametri dalla richiesta
    	// Parametri da richiesta HTTP
        //int n_ordine = Integer.parseInt(request.getParameter("n_ordine"));
        String nome_utente = request.getParameter("nome_utente");
        String metodo_pagamento = request.getParameter("metodo_pagamento");
        float totale = Float.parseFloat(request.getParameter("totale"));
        String data_ordine_str = request.getParameter("data_ordine");
        boolean isCartCreated = (boolean)(session.getAttribute("isCartCreated"));
        byte[] immagine = null; // Da implementare la gestione dell'immagine correttamente
        // Conversione della data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data_ordine = null;
        try {
            data_ordine = (Date) sdf.parse(data_ordine_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        
        
        // Crea un nuovo oggetto Carrello_bean
        if(!isCartCreated) {
        	Carrello_bean carrello = new Carrello_bean();
        	//carrello.set_n_ordine(n_ordine);
        	carrello.set_nome_utente(nome_utente);
        	carrello.set_metodo_pagamento(metodo_pagamento);
        	carrello.set_totale(totale);
        	carrello.set_data_ordine(data_ordine);
        	carrello.setImmagine(immagine);
        	carrello.addGame(idGioco);

        // Salva il carrello nel database
        	try {
        		carrelloDAO.doSave(carrello);
            
        		//settiamo il nuovo oggetto da mostrare nel carrello
            
        		session.setAttribute("carrello", carrello);
            
        		RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
        		dispatcher.forward(request, response);
        	} catch (SQLException e) {
        		throw new ServletException(e);
        	}
        } else {
        	Carrello_bean carrello = (Carrello_bean) session.getAttribute("carrello");
        	
        	carrello.addGame(idGioco);
        }
        
        
    }

    private void eliminaElemento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera il parametro dalla richiesta
        int n_ordine = Integer.parseInt(request.getParameter("n_ordine"));
        
        // Elimina l'elemento dal carrello nel database
        try {
            carrelloDAO.doDelete(n_ordine);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    /**
     * Recupera i dati essenziali di un gioco dal database e li restituisce come una stringa formattata.
     * Se il gioco non viene trovato o se si verifica un errore durante il recupero, restituisce un messaggio appropriato.
     * 
     * @param idGioco ID del gioco da recuperare
     * @return Stringa contenente i dati essenziali del gioco, oppure un messaggio di errore
     */
    public String RecuperaDatiGioco(int idGioco) {
        try {
            // Recupera il gioco dal database utilizzando il DAO appropriato
            Game_bean gioco = gameDAO.doRetrieveByKey(idGioco);

            if (gioco != null) {
                // Costruisci la stringa con i dati essenziali del gioco
                return "ID: " + gioco.get_id_gioco() +
                       ", Nome: " + gioco.get_nome() +
                       ", Prezzo: " + gioco.get_prezzo();
                // Aggiungi altri attributi se necessario
            } else {
                // Se il gioco non viene trovato, restituisce un messaggio
                return null;
            }

        } catch (SQLException e) {
            // Gestione dell'eccezione SQLException
            e.printStackTrace(); // Stampa il trace dell'eccezione per il debugging
            return "Errore durante il recupero dei dati del gioco";
        } catch (Exception e) {
            // Gestione di altre eccezioni generiche
            e.printStackTrace(); // Stampa il trace dell'eccezione per il debugging
            return "Errore generale";
        }
    }

    
    public void destroy() {
        super.destroy();
    }
}
