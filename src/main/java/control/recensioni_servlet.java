package control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import model.Game_DAODataSource;
import model.Game_bean;
import model.Recensioni_DAODataSource;
import model.Recensioni_bean;

public class recensioni_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Game_DAODataSource gameDAO;
    private Recensioni_DAODataSource recensioniDAO;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        gameDAO = new Game_DAODataSource(ds);
        recensioniDAO = new Recensioni_DAODataSource(ds);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mostraRecensioni(request, response);
        System.out.println("doget recensioni arrivato"); 
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("recensione"); 
        
        if ("add_recensione".equals(action)) {
            aggiungiRecensione(request, response);
        } else {
            mostraRecensioni(request, response);
        }
    }

    private void mostraRecensioni(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
      //  int idGioco = Integer.parseInt(request.getParameter("gioco_aggiungi_recensione"));
        System.out.println("mostra recensioni letto"); 
        try {
            Collection<Recensioni_bean> recensioni = recensioniDAO.doRetrieveAll();
            request.setAttribute("recensioni", recensioni);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Dettaglio_prodotto.jsp");
            dispatcher.forward(request, response);
            System.out.println("mostra recensioni eseguito"); 
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestire l'errore e inoltrare a una pagina di errore se necessario
        }
    }

    private void aggiungiRecensione(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idGioco = Integer.parseInt(request.getParameter("gioco_aggiungi_recensione"));
        String nome_utente = request.getParameter("nome_utente_recensione");
        String testo = request.getParameter("review");
        String messaggio=null; 
        
        Game_bean gioco = null;
        try {
            gioco = gameDAO.doRetrieveByKey(idGioco);
        } catch (SQLException e) {
            e.printStackTrace(); // Gestisci l'eccezione in base alle tue esigenze
        }
        request.setAttribute("gioco", gioco); // Passa l'oggetto gioco alla JSP

        Recensioni_bean nuovaRecensione = new Recensioni_bean();
        nuovaRecensione.set_nome_utente(nome_utente);
        nuovaRecensione.set_id_gioco(idGioco);
        nuovaRecensione.set_testo(testo);
        
        try {
            recensioniDAO.doSave(nuovaRecensione);
            request.setAttribute("messaggio", "recensione registrata con successo");
            
           
            // Dopo aver aggiunto la recensione, visualizza le recensioni aggiornate
            //mostraRecensioni(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestire l'errore e inoltrare a una pagina di errore se necessario
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Dettaglio_prodotto.jsp");
        dispatcher.forward(request, response);
    }

    public void destroy() {
        super.destroy();
    }
}
