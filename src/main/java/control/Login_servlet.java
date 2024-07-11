package control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;

import model.Game_DAODataSource;
import model.Utenti_DAODataSource;
import model.Utenti_bean;

//@WebServlet("/Login_servlet")
public class Login_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Utenti_DAODataSource utenti;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
       // gameDAO = new Game_DAODataSource(getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
    DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
    utenti = new Utenti_DAODataSource(ds);
           
        String username = request.getParameter("username");
        System.out.println("username" + username);
        String password = request.getParameter("password");
        System.out.println("password" + password);
        

        try {
            Utenti_bean utente = utenti.verificaCredenziali(username, password);
            
            HttpSession session = request.getSession();

            if (utente != null) {
            	; //se l'utente con il seguente nome utente e password è presente nel db, allora creiamo la sessione
                session.setAttribute("utente", utente);   
                session.setAttribute("loginAttempted", true); //se l'utente non ha la sessione, la crea
               
                
                // Determina la destinazione in base al tipo di utente
                String tipoUtente = utente.get_tipo(); // recuperiamo il tipo   "admin" o "base"
                session.setAttribute("tipo", tipoUtente); //facciamo in modo che il tipo dell'utente sia recuperabile per tutta la durata della sessione
                
                if ("admin".equals(tipoUtente)) {
                    // Reindirizza a Profilo_admin.jsp usando il dispatcher
                    RequestDispatcher dispatcher = request.getRequestDispatcher("scripts/Profilo_admin.jsp");
                    dispatcher.forward(request, response);

                    // Reindirizza a storico.jsp usando un altro dispatcher
                    RequestDispatcher storicoDispatcher = request.getRequestDispatcher("/scripts/storico.jsp");
                    storicoDispatcher.forward(request, response);
                } else if ("base".equals(tipoUtente)) {
                    // Reindirizza a Profilo_utente.jsp usando il dispatcher
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Profilo_utente.jsp");
                    dispatcher.forward(request, response);
                } else {
                    // Gestione altri tipi di utente, se necessario
                    session.setAttribute("login-error", "Tipo di utente non gestito");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Pagina_login.jsp");
                    dispatcher.forward(request, response);
                }
            } else {
                request.setAttribute("login-error", "Credenziali non valide");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Pagina_login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            HttpSession session = request.getSession();
            e.printStackTrace();
            session.setAttribute("login-error", "Errore del server");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Pagina_login.jsp");
            dispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Inoltra alla pagina di login per le richieste GET
        RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Pagina_login.jsp");
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

            // E altri campi dell'utente che desideri aggiornare...

            // Aggiorna l'utente nel database utilizzando il DAO
            utenti.update(utente);

            // Aggiornamento completato con successo
            request.setAttribute("updateSuccess", true);
        } else {
            // Utente non trovato nel database, gestire l'errore o l'eccezione
            request.setAttribute("updateError", "Utente non trovato nel database");
        }

        // Utilizza il dispatcher per inoltrare la richiesta alla pagina desiderata
        RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Pagina_di_conferma.jsp");
        dispatcher.forward(request, response);
    }


    public void destroy() {
        super.destroy();
        // Eventuale chiusura risorse
    }

}