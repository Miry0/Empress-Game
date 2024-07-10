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
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;

import model.Game_bean;
import model.Game_DAODataSource;

//@WebServlet("/Gestione_giochi_servlet")
public class Gestione_giochi_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Game_DAODataSource gameDAO;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
       // gameDAO = new Game_DAODataSource(getServletContext());
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
            gameDAO = new Game_DAODataSource(ds);
            
        try {
        	
        
            // Recupera il parametro "order" dalla richiesta
            String order = request.getParameter("order");

            // Recupera la lista dei giochi dal DAO
            Collection<Game_bean> listaGiochi = gameDAO.doRetrieveAll(order);
            
          
         // Converte la collezione in una lista per poterla ordinare
            List<Game_bean> listaGiochi2 = new ArrayList<>(listaGiochi);
            
            // Ordina la lista in base al parametro "order"
            if ("name".equals(order)) {
                listaGiochi2.sort((g1, g2) -> g1.get_nome().compareToIgnoreCase(g2.get_nome()));
            } else if ("prezzo".equals(order)) {
                listaGiochi2.sort((g1, g2) -> Double.compare(g1.get_prezzo(), g2.get_prezzo()));
            }
            // Puoi aggiungere altri criteri di ordinamento qui
            
            // Imposta la lista dei giochi come attributo della richiesta per la JSP
            request.setAttribute("listaGiochi", listaGiochi);

            // Imposta il parametro "order" nella richiesta per essere utilizzato nella JSP
            request.setAttribute("order", order);

            // Inoltra la richiesta alla JSP per mostrare i giochi
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace(); // Gestisci l'eccezione in base alla tua logica
            request.setAttribute("error", "Errore durante il recupero dei giochi.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ottiene l'azione richiesta dalla richiesta HTTP
    	 DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
         gameDAO = new Game_DAODataSource(ds);
         
      String action = request.getParameter("submitAction");
       System.out.println("1action " + action);
        
        try {
            // Gestisce le diverse azioni in base al parametro 'submitAction'
            if ("Aggiungi".equals(action)) {
                addGame(request, response); // Aggiunge un nuovo gioco
            } else if ("Modifica".equals(action)) {
                updateGame(request, response); // Aggiorna un gioco esistente
            } else if ("Elimina".equals(action)) {
                deleteGame(request, response); // Elimina un gioco esistente
            }
            else if("search".equals(action)) {
            	Ricerca(request, response);
            	System.out.println("2action " + action);
            	
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
   
    
    private void Ricerca(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
        gameDAO = new Game_DAODataSource(ds);
        
    	//String order = request.getParameter("order");
    	String name=request.getParameter("nomeGioco");//recuperiamo il nome del gioco dalla barra di ricerca
    	//Collection<Game_bean> listaGiochi = gameDAO.doRetrieveAll(order);
    	 System.out.println("Nome del gioco cercato: " + name);
    	 
    	List<Game_bean> listaGiochi=new LinkedList<>();
    	
    	try {
    	listaGiochi=gameDAO.searchGamesByName(name); //chiamoiamo la ricerca con la fujnzione del dao
    	System.out.println("Lista dei giochi : " + listaGiochi);
    	}
    	catch(SQLException e) {
    		System.out.println("la lista dei giochi nella servlet è" + listaGiochi);
    	}
    	request.setAttribute("listaGiochi", listaGiochi);
    	 request.getRequestDispatcher("scripts/Risultati_ricerca.jsp").forward(request, response); //gestiamo i risultati nella jsp designata
    	
    }
    
  }

