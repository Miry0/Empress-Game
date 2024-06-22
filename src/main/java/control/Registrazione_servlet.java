//servlet che gestisce la registrazione degli utenti; 
package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Utenti_DAODataSource;
import model.Utenti_bean;

@WebServlet("/Registrazione_servlet/")
public class Registrazione_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private Utenti_DAODataSource utenti; // DAO per l'interazione con il database degli utenti
    
    public void init() throws ServletException {
    	//inizializziamo le risorse che la servlet userà nel suo ciclo di vita; 
    	utenti = new Utenti_DAODataSource(); // Inizializzazione del DAO all'avvio della servlet
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera i parametri dal form di registrazione
    	
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String password = request.getParameter("password");
        String tipo = "base"; // Assegna tipo base di default. Questo significa che stiamo registrando ujn utente base. 
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
            
            // Reindirizzamento alla pagina di conferma registrazione
            response.sendRedirect("registrazione_successo.jsp");
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
        // Chiusura risorse se necessario
    	//è un metodo generico delle servlet.
    }
}

