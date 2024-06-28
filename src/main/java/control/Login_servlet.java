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

@WebServlet("/Login_servlet")
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
        session.setAttribute("loginAttempted", true);

        try {
            Utenti_bean utente = utenti.verificaCredenziali(username, password);

            if (utente != null) {
                session.setAttribute("utente", utente);
                // Utilizzo di RequestDispatcher per inoltrare a login_successo.
                //in auesto modo, il client non vedrà il cambio di URL
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("login_successo.jsp");
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

    public void destroy() {
        super.destroy();
        // Eventuale chiusura risorse
    }

}

