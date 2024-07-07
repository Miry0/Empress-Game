package control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;

import model.Utenti_DAODataSource;
import model.Utenti_bean;

//@WebServlet("/Login_servlet")
public class Login_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Utenti_DAODataSource utenti;

    public void init() throws ServletException {
        super.init();
        // Inizializzazione del DAO per l'interazione con il database
        utenti = new Utenti_DAODataSource();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        session.setAttribute("loginAttempted", true);  //validazione della sessione creata per l'utente

        try {
            Utenti_bean utente = utenti.verificaCredenziali(username, password); //verifica delle credenziali passate dall'utente

            if (utente != null) {
                session.setAttribute("utente", utente);
                // Utilizzo di RequestDispatcher per inoltrare a login_successo.
                //in questo modo, il client non vedrà il cambio di URL
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("Login_successo.jsp");
                dispatcher.forward(request, response);
            } else {
                session.setAttribute("login-error", "Credenziali non valide");
                // Utilizzo di RequestDispatcher per inoltrare a Pagina_login.jsp con messaggio di errore
                RequestDispatcher dispatcher = request.getRequestDispatcher("Pagina_login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("login-error", "Errore del server");
            // Utilizzo di RequestDispatcher per inoltrare a Pagina_login.jsp con messaggio di errore
            RequestDispatcher dispatcher = request.getRequestDispatcher("Pagina_login.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Inoltra alla pagina di login per le richieste GET
        RequestDispatcher dispatcher = request.getRequestDispatcher("Pagina_login.jsp");
        dispatcher.forward(request, response);
    }
    
    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        // Recupera l'utente dal database tramite il suo identificativo
        String nome_utente = request.getParameter("username"); // Supponiamo che "username" sia il parametro per identificare l'utente
        Utenti_bean utente = utenti.doRetrieveByKey(nome_utente); // Sostituisci con il metodo corretto per recuperare l'utente dal DAO

        if (utente != null) {
            // Se l'utente esiste nel database, aggiorna i suoi dati se presenti nei parametri della richiesta
            String nome = request.getParameter("nome");
            if (nome != null && !nome.trim().isEmpty()) {
                utente.set_nome(nome);
            }

            String cognome = request.getParameter("cognome");
            if (cognome != null && !cognome.trim().isEmpty()) {
                utente.set_cognome(cognome);
            }

            String email = request.getParameter("email");
            if (email != null && !email.trim().isEmpty()) {
                utente.set_email(email);
            }

            String password = request.getParameter("password");
            if (password != null && !password.trim().isEmpty()) {
                utente.set_password(password);
            }
            
            
            String g_nascitaStr = request.getParameter("g_nascita");//restituisce una stringa che rappresenta il valore del parametro g_nascita passato nella richiesta HTTP
            if (g_nascitaStr != null && !g_nascitaStr.trim().isEmpty()) {
                try {
                    int g_nascita = Integer.parseInt(g_nascitaStr);
                    utente.set_g_nascita(g_nascita);
                } catch (NumberFormatException e) { //se la conversione in intero non va a buon fine, si solleva l'eccezione
                   
                    // diamo errore direttamente sul campo
                }
            }
            
            String m_nascitaStr = request.getParameter("m_nascita");//restituisce una stringa che rappresenta il valore del parametro g_nascita passato nella richiesta HTTP
            if (m_nascitaStr != null && !m_nascitaStr.trim().isEmpty()) {
                try {
                    int m_nascita = Integer.parseInt(m_nascitaStr);
                    utente.set_g_nascita(m_nascita);
                } catch (NumberFormatException e) { //se la conversione in intero non va a buon fine, si solleva l'eccezione
                   
                    // diamo errore direttamente sul campo
                }
            }
            
            String a_nascitaStr = request.getParameter("a_nascita");//restituisce una stringa che rappresenta il valore del parametro g_nascita passato nella richiesta HTTP
            if (a_nascitaStr != null && !a_nascitaStr.trim().isEmpty()) {
                try {
                    int a_nascita = Integer.parseInt(a_nascitaStr);
                    utente.set_g_nascita(a_nascita);
                } catch (NumberFormatException e) { //se la conversione in intero non va a buon fine, si solleva l'eccezione
                   
                    // diamo errore direttamente sul campo
                }
            }
            

            // Aggiorna l'utente nel database utilizzando il DAO
            utenti.update(utente);

            // Aggiornamento completato con successo
            request.setAttribute("updateSuccess", true);
        } else {
            // Utente non trovato nel database, gestire l'errore o l'eccezione
            request.setAttribute("updateError", "Utente non trovato nel database");
        }

        // Utilizza il dispatcher per inoltrare la richiesta alla pagina Profilo_utente
        RequestDispatcher dispatcher = request.getRequestDispatcher("Profilo_utente.jsp");
        dispatcher.forward(request, response);
    }


    public void destroy() {
        super.destroy();
        // Eventuale chiusura risorse
    }

}