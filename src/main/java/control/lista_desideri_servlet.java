package control;

import model.Sta_nella_lista_bean;
import model.Sta_nella_lista_DAODataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/wishlist")
public class lista_desideri_servlet extends HttpServlet {

    private static DataSource ds;

    static {
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");

            ds = (DataSource) envCtx.lookup("jdbc/storage");

        } catch (NamingException e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    private Sta_nella_lista_DAODataSource staNellaListaDAO = new Sta_nella_lista_DAODataSource();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nomeUtente = request.getParameter("nome_utente");

        try {
            viewWishlist(request, response, nomeUtente);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
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
                    String nomeUtente = request.getParameter("nome_utente");
                    viewWishlist(request, response, nomeUtente);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    // Metodo per aggiungere un prodotto alla lista desideri
    private void addToWishlist(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String nomeGioco = request.getParameter("nome_gioco");
        String nomeUtente = request.getParameter("nome_utente");
        int idLista = Integer.parseInt(request.getParameter("id_lista"));

        int idGioco = staNellaListaDAO.getGameIdFromName(nomeGioco); // Ottieni l'ID del gioco dal nome

        if (idGioco != -1) {
            Sta_nella_lista_bean staNellaLista = new Sta_nella_lista_bean();
            staNellaLista.set_id_lista(idLista);
            staNellaLista.set_nome_utente(nomeUtente);
            staNellaLista.set_id_gioco(idGioco);

            byte[] immagine = staNellaListaDAO.retrieveGameImage(idGioco); // Recupera l'immagine associata al gioco
            staNellaLista.set_immagine(immagine); // Imposta l'immagine nel bean

            staNellaListaDAO.doSave(staNellaLista); // Salva il bean nella lista dei desideri
        }

        viewWishlist(request, response, nomeUtente); // Visualizza nuovamente la lista dei desideri
    }
    
    // Metodo per rimuovere un prodotto dalla lista desideri
    private void removeFromWishlist(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String nomeGioco = request.getParameter("nome_gioco");
        String nomeUtente = request.getParameter("nome_utente");
        int idLista = Integer.parseInt(request.getParameter("id_lista"));

        int idGioco = staNellaListaDAO.getGameIdFromName(nomeGioco); // Ottieni l'ID del gioco dal nome

        if (idGioco != -1) {
            staNellaListaDAO.deleteByListIdAndGameId(idLista, nomeUtente, idGioco); // Elimina il gioco dalla lista dei desideri
        }

        viewWishlist(request, response, nomeUtente); // Visualizza nuovamente la lista dei desideri
    }

    // Metodo per visualizzare la lista dei desideri di un utente
    private void viewWishlist(HttpServletRequest request, HttpServletResponse response, String nomeUtente) throws SQLException, ServletException, IOException {
        Collection<Sta_nella_lista_bean> wishlist = staNellaListaDAO.getWishlistByUser(nomeUtente); // Ottieni la lista dei desideri dall'utente
        request.setAttribute("wishlist", wishlist); // Imposta l'attributo nella richiesta
        request.getRequestDispatcher("/wishlist.jsp").forward(request, response); // Inoltra alla pagina JSP per la visualizzazione
    }
}
