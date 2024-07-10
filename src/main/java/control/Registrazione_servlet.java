//servlet che gestisce la registrazione degli utenti; 
package control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import model.Utenti_DAODataSource;
import model.Utenti_bean;

//@WebServlet("/Registrazione_servlet")
public class Registrazione_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;;
    
    private Utenti_DAODataSource utenti; // DAO per l'interazione con il database degli utenti
    
    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
       // gameDAO = new Game_DAODataSource(getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri dal form di registrazione
    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
        utenti = new Utenti_DAODataSource(ds);
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String password = request.getParameter("password");
        String tipo = "base"; // Assegna tipo base di default. Questo significa che stiamo registrando un utente base. 
        //se vogliamo inserire un admin, lo andremo a modificare; 
        int giornoNascita = Integer.parseInt(request.getParameter("g_nascita"));
        int meseNascita = Integer.parseInt(request.getParameter("m_nascita"));
        int annoNascita = Integer.parseInt(request.getParameter("a_nascita"));
        
        // Crea un nuovo Utenti_bean con i dati ricevuti 
        Utenti_bean nuovoUtente = new Utenti_bean();
        nuovoUtente.set_nome(nome);
        nuovoUtente.set_cognome(cognome);
        nuovoUtente.set_password(password);
        nuovoUtente.set_tipo(tipo);
        nuovoUtente.set_g_nascita(giornoNascita);
        nuovoUtente.set_m_nascita(meseNascita);
        nuovoUtente.set_a_nascita(annoNascita);
        
        try {
            // Salva il nuovo utente nel database utilizzando il DAO
            utenti.doSave(nuovoUtente);
            
            request.setAttribute("nuovoUtente", nuovoUtente);
            // Reindirizzamento alla pagina di conferma registrazione
            request.getRequestDispatcher("scripts/Successo_registrazione.jsp").forward(request, response);
        } catch (SQLException e) {
            // Gestione dell'eccezione SQL
            e.printStackTrace();
            response.sendRedirect("registrazione_fallita.jsp"); // Reindirizza a pagina di errore
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gestione GET, reindirizzamento a pagina di errore o altro se necessario
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Metodo non supportato.");
    }
	
	public void destroy() {
		//liberiamo le risorse usate; 
		super.destroy(); 
	}
}
