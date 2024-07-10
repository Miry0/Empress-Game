//package control; 

/*
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Game_bean;
import model.Game_DAODataSource;

//servlet che gestisce la ricerca di un gioco dalla barra della home
//@WebServlet("/RicercaServlet")
public class Ricerca_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Variabile per il DAO dei giochi
    private Game_DAODataSource gameDAO;

    // Metodo init della servlet
    public void init() throws ServletException {
        // Inizializzazione del DAO dei giochi
    	 DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
         gameDAO = new Game_DAODataSource(ds);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Gestione delle richieste GET reindirizzate alla POST
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Leggi il parametro di ricerca dalla richiesta
        String searchQuery = request.getParameter("searchQuery"); //acquisizine del parametro da usare per la ricerca

        // Esegui la ricerca dei giochi
        List<Game_bean> risultatiRicerca = null;
        if (searchQuery != null && !searchQuery.isEmpty()) { //se l'input inserito non è vuoto o null
            risultatiRicerca = ricercaGioco(searchQuery); //allora richiamiamo la funzione ricercaGioco, che utilizza un metodo del DAO per effettuare la ricerca
        }

        // Memorizza i risultati della ricerca nella richiesta come attributo
        request.setAttribute("risultatiRicerca", risultatiRicerca);

        // Inoltra la richiesta alla JSP di visualizzazione dei risultati di ricerca
        RequestDispatcher dispatcher = request.getRequestDispatcher("Risultati_ricerca.jsp"); //indirizziamo il risultato del metodo alla jsp 
        dispatcher.forward(request, response);												//che fornisce i contenuti che rispettano la ricerca del client
    }
    
    private List<Game_bean> ricercaGioco(String query) {
        try {
            // Esegui la ricerca dei giochi nel DAO
            return gameDAO.searchGamesByName(query);
        } catch (Exception e) {
            e.printStackTrace(); // Gestione dell'errore in base alle tue esigenze
            return null;
        }
    }
    
    // Metodo destroy della servlet
    public void destroy() {
        // Rilascio delle risorse (se necessario)
        gameDAO = null;
    }
}
*/