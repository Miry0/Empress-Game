package control; 

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Carrello_DAODataSource;
import model.Carrello_bean;

//@WebServlet("/CarrelloServlet")
public class Carrello_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Carrello_DAODataSource carrelloDAO;

    public void init() throws ServletException {
        // Inizializza il DAO del carrello con il DataSource dal context
        carrelloDAO = new Carrello_DAODataSource(getServletContext());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Se l'azione non è definita, mostra il carrello
            mostraCarrello(request, response);
        } else {
            switch (action) {
                default:
                    mostraCarrello(request, response);
                    break;
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "aggiungi":
                    aggiungiElemento(request, response);
                    break;
                case "elimina":
                    eliminaElemento(request, response);
                    break;
                default:
                	RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
                    dispatcher.forward(request, response);                    
                    break;
            }
        } else {
        	RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
            dispatcher.forward(request, response);        }
    }

    private void mostraCarrello(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Collection<Carrello_bean> carrello = carrelloDAO.doRetrieveAll(null);
            request.setAttribute("carrello", carrello);
            RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_carrello.jsp");
            dispatcher.forward(request, response);
            } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void aggiungiElemento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recupera i parametri dalla richiesta
        int n_ordine = Integer.parseInt(request.getParameter("n_ordine"));
        int id_utente = Integer.parseInt(request.getParameter("id_utente"));
        String metodo_pagamento = request.getParameter("metodo_pagamento");
        float totale = Float.parseFloat(request.getParameter("totale"));
        int g_ordine = Integer.parseInt(request.getParameter("g_ordine"));
        int m_ordine = Integer.parseInt(request.getParameter("m_ordine"));
        int a_ordine = Integer.parseInt(request.getParameter("a_ordine"));
        byte[] immagine = request.getParameter("immagine").getBytes(); // Da verificare

        // Crea un nuovo oggetto Carrello_bean
        Carrello_bean carrello = new Carrello_bean();
        carrello.set_n_ordine(n_ordine);
        carrello.set_id_utente(id_utente);
        carrello.set_metodo_pagamento(metodo_pagamento);
        carrello.set_totale(totale);
        carrello.set_g_ordine(g_ordine);
        carrello.set_m_ordine(m_ordine);
        carrello.set_a_ordine(a_ordine);
        carrello.setImmagine(immagine);

        // Salva il carrello nel database
        try {
            carrelloDAO.doSave(carrello);
            
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
