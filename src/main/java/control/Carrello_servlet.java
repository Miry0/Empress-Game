package control; 

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Carrello_DAODataSource;
import model.Carrello_bean;
import model.Game_DAODataSource;

//@WebServlet("/CarrelloServlet")
public class Carrello_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Carrello_DAODataSource carrelloDAO;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
       // gameDAO = new Game_DAODataSource(getServletContext());
    }

 //gestiamo il carrello direttamente con il metodo dopost
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
         carrelloDAO = new Carrello_DAODataSource(ds);
         
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
                	 mostraCarrello(request, response);
                	//RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
                    //dispatcher.forward(request, response);                    
                    break;
            }
        } else {
            mostraCarrello(request, response);
        }
    }
    

    private void mostraCarrello(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Collection<Carrello_bean> carrello = carrelloDAO.doRetrieveAll(null); //recuperiamo il contenuto del carrello
            request.setAttribute("carrello", carrello); //settiamo l'attributo carrello che verrà recuperato nella jsp
            RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp"); //reindirizziamo alla jsp
            dispatcher.forward(request, response);
            } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void aggiungiElemento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera i parametri dalla richiesta
    	// Parametri da richiesta HTTP
        int n_ordine = Integer.parseInt(request.getParameter("n_ordine"));
        String nome_utente = request.getParameter("nome_utente");
        String metodo_pagamento = request.getParameter("metodo_pagamento");
        float totale = Float.parseFloat(request.getParameter("totale"));
        String data_ordine_str = request.getParameter("data_ordine");
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
        Carrello_bean carrello = new Carrello_bean();
        carrello.set_n_ordine(n_ordine);
        carrello.set_nome_utente(nome_utente);
        carrello.set_metodo_pagamento(metodo_pagamento);
        carrello.set_totale(totale);
        carrello.set_data_ordine(data_ordine);
        carrello.setImmagine(immagine);

        // Salva il carrello nel database
        try {
            carrelloDAO.doSave(carrello);
            
            HttpSession session = request.getSession(); //recuperiamo la sessione e settiamo il nuovo oggetto da mostrare nel carrello
            
            session.setAttribute("carrello", carrello);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
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
    
    public void destroy() {
        super.destroy();
    }
}
