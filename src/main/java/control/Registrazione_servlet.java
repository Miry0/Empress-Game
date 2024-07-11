//servlet che gestisce la registrazione degli utenti; 
package control;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        
        System.out.println("utenti"+ utenti);
        
        String nome_utente=request.getParameter("nome_utente");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String password = request.getParameter("pass");
        String tipo = "base"; // Assegna tipo base di default. Questo significa che stiamo registrando un utente base. 
        //se vogliamo inserire un admin, lo andremo a modificare; 
        String email=request.getParameter("email");
        int giornoNascita = Integer.parseInt(request.getParameter("g_nascita"));
        int meseNascita = Integer.parseInt(request.getParameter("m_nascita"));
        int annoNascita = Integer.parseInt(request.getParameter("a_nascita"));
        
        String hashedPassword=toHash(password); //criptiamo password
        
        // Crea un nuovo Utenti_bean con i dati ricevuti 
        Utenti_bean nuovoUtente = new Utenti_bean();
        nuovoUtente.set_nome_utente(nome_utente);
        nuovoUtente.set_nome(nome);
        nuovoUtente.set_cognome(cognome);
        nuovoUtente.set_password(hashedPassword);
        nuovoUtente.set_email(email);
        nuovoUtente.set_tipo(tipo);
        nuovoUtente.set_g_nascita(giornoNascita);
        nuovoUtente.set_m_nascita(meseNascita);
        nuovoUtente.set_a_nascita(annoNascita);
        
        System.out.println("nuovoUtente"+ nuovoUtente);
        
        try {
            // Salva il nuovo utente nel database utilizzando il DAO
            utenti.doSave(nuovoUtente);
            
            //ci sarà un tasto che riporta all'home e l'utente dovrà effettuare l'accesso
            // Reindirizzamento alla pagina di conferma registrazione
            request.getRequestDispatcher("/scripts/Successo_registrazione.jsp").forward(request, response);
        } catch (SQLException e) {
            // Gestione dell'eccezione SQL
            e.printStackTrace();
            request.getRequestDispatcher("index.jsp").forward(request, response);
            	// Reindirizza a pagina di errore
        }
    }
    
    private String toHash(String password) {
    	String hashString=null;
    	
    	try {
    		java.security.MessageDigest digest=java.security.MessageDigest.getInstance("SHA-512");
    		byte [] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    		
    		hashString=""; for(int i=0; i<hash.length; i++) {
    			hashString+=Integer.toHexString(hash[i] & 0xFF | 0x100).substring(1,3);
    		}
    	}
    		catch(java.security.NoSuchAlgorithmException e) {
        	}
    		return hashString;
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
