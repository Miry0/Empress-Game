package control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.RequestDispatcher;
import model.Game_bean;
import model.Game_DAODataSource;

//@WebServlet("/Gestione_giochi_servlet")
public class Gestione_giochi_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Game_DAODataSource gameDAO;

    public void init() throws ServletException {
        super.init();
        // Inizializzazione del DAO per interagire con il database dei giochi
        gameDAO = new Game_DAODataSource();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ottiene l'azione richiesta dalla richiesta HTTP
        String action = request.getParameter("submitAction");

        try {
            // Gestisce le diverse azioni in base al parametro 'submitAction'
            if ("Aggiungi".equals(action)) {
                addGame(request, response); // Aggiunge un nuovo gioco
            } else if ("Modifica".equals(action)) {
                updateGame(request, response); // Aggiorna un gioco esistente
            } else if ("Elimina".equals(action)) {
                deleteGame(request, response); // Elimina un gioco esistente
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e); // Gestisce le eccezioni SQL
        }
    }

    // Metodo per aggiungere un nuovo gioco al catalogo
    private void addGame(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        // Recupera i parametri dalla richiesta HTTP
        String nome = request.getParameter("nome");
        String piattaforma = request.getParameter("piattaforma");
        String genere = request.getParameter("genere");
        float prezzo = Float.parseFloat(request.getParameter("prezzo"));
        int gUscita = Integer.parseInt(request.getParameter("g_uscita"));
        int mUscita = Integer.parseInt(request.getParameter("m_uscita"));
        int aUscita = Integer.parseInt(request.getParameter("a_uscita"));

        // Recupera l'immagine come array di byte
        byte[] immagine = extractImageBytes(request);

        // Crea un oggetto Game_bean con i dati ricevuti dalla richiesta
        Game_bean game = new Game_bean();
        game.set_nome(nome);
        game.set_piattaforma(piattaforma);
        game.set_genere(genere);
        game.set_prezzo(prezzo);
        game.set_g_uscita(gUscita);
        game.set_m_uscita(mUscita);
        game.set_a_uscita(aUscita);
        game.setImmagine(immagine);

        // Salva il gioco nel database utilizzando il DAO
        gameDAO.doSave(game);

        // Utilizza il dispatcher per inoltrare la richiesta alla pagina Gestione_catalogo.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_catalogo.jsp");
        dispatcher.forward(request, response);
    }

    // Metodo per aggiornare un gioco nel catalogo
    private void updateGame(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        // Recupera l'id del gioco da aggiornare
        int id = Integer.parseInt(request.getParameter("id"));
        // Recupera il gioco dal database tramite l'id
        Game_bean game = gameDAO.doRetrieveByKey(id);

        if (game != null) {
            // Se il gioco esiste nel database, aggiorna i suoi dati se presenti nei parametri della richiesta
            String nome = request.getParameter("nome");
            if (nome != null && !nome.trim().isEmpty()) {
                game.set_nome(nome);
            }

            String piattaforma = request.getParameter("piattaforma");
            if (piattaforma != null && !piattaforma.trim().isEmpty()) { //trim elimina spazi bianchi iniziali e finali
                game.set_piattaforma(piattaforma);
            }

            String genere = request.getParameter("genere");
            if (genere != null && !genere.trim().isEmpty()) {
                game.set_genere(genere);
            }

            String prezzoStr = request.getParameter("prezzo");
            if (prezzoStr != null && !prezzoStr.trim().isEmpty()) {
                float prezzo = Float.parseFloat(prezzoStr);
                game.set_prezzo(prezzo);
            }

            String gUscitaStr = request.getParameter("g_uscita");
            if (gUscitaStr != null && !gUscitaStr.trim().isEmpty()) {
                int gUscita = Integer.parseInt(gUscitaStr);
                game.set_g_uscita(gUscita);
            }

            String mUscitaStr = request.getParameter("m_uscita");
            if (mUscitaStr != null && !mUscitaStr.trim().isEmpty()) {
                int mUscita = Integer.parseInt(mUscitaStr);
                game.set_m_uscita(mUscita);
            }

            String aUscitaStr = request.getParameter("a_uscita");
            if (aUscitaStr != null && !aUscitaStr.trim().isEmpty()) {
                int aUscita = Integer.parseInt(aUscitaStr);
                game.set_a_uscita(aUscita);
            }

            // Recupera l'immagine come array di byte
            byte[] immagine = extractImageBytes(request);
            game.setImmagine(immagine);

            // Aggiorna il gioco nel database utilizzando il DAO
            gameDAO.update(game);
        }

        // Utilizza il dispatcher per inoltrare la richiesta alla pagina Gestione_catalogo.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_catalogo.jsp"); 
        dispatcher.forward(request, response);
    }

    // Metodo per eliminare un gioco dal catalogo
    private void deleteGame(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        // Recupera l'id del gioco da eliminare
        int id = Integer.parseInt(request.getParameter("id"));
        // Elimina il gioco dal database utilizzando il DAO
        gameDAO.doDelete(id);

        // Utilizza il dispatcher per inoltrare la richiesta alla pagina Gestione_catalogo.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("Gestione_catalogo.jsp");
        dispatcher.forward(request, response);
    }

    // Metodo per estrarre l'immagine dalla richiesta HTTP come array di byte
    private byte[] extractImageBytes(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("immagine"); // Recupera il Part relativo all'immagine dal form
        InputStream inputStream = filePart.getInputStream(); // Ottiene lo stream di input dall'immagine
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096]; // Buffer per la lettura dell'immagine
        int bytesRead = -1;

        // Legge l'immagine dallo stream e la scrive nell'outputStream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] immagine = outputStream.toByteArray(); // Converte l'outputStream in un array di byte
        outputStream.close();
        inputStream.close();

        return immagine;
    }

    public void destroy() {
        super.destroy();
    }
}
