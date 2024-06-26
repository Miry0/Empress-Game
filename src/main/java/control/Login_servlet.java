package control;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Utenti_DAODataSource;
import model.Utenti_bean;

@WebServlet("/Login_servlet")
public class Login_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Utenti_DAODataSource utenti;

    public void init() throws ServletException {
        super.init(); 
        // Inizializzazione del DAO per l'interazione con il database
        utenti = new Utenti_DAODataSource(); // instanziamo un collegamento tra servlet e db attraverso il DAO, sulla tabella UTENTI
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // estraiamo i parametri username e password dal form di login
        String username = request.getParameter("username"); 
        String password = request.getParameter("password");

        HttpSession session = request.getSession();
        session.setAttribute("loginAttempted", "true");

        try {
            // Verifica le credenziali
            Utenti_bean utente = utenti.verificaCredenziali(username, password);

            if (utente != null) {
                // Credenziali corrette, crea una sessione per l'utente
                session.setAttribute("registeredUser", utente);
                session.removeAttribute("login-error");
                // Redirige alla pagina di successo
                response.sendRedirect("index.jsp"); // indirizziamo l'utente alla pagina principale
            } else {
                // Credenziali errate, imposta l'attributo di errore e redirige alla pagina di login
                session.setAttribute("login-error", "Credenziali non valide");
                response.sendRedirect("login.jsp");
            }
        } catch (SQLException e) {
            // Gestione dell'eccezione SQL
            e.printStackTrace();
            session.setAttribute("login-error", "Errore del server");
            response.sendRedirect("login.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gestione GET, redirige alla pagina di login
        response.sendRedirect("login.jsp");
    }

    public void destroy() {
        super.destroy(); 
        // Chiusura risorse se necessario
    }
}
