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

//@WebServlet("/Login_servlet")
public class Logout_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Utenti_DAODataSource utenti;

    public void init() throws ServletException {
    	super.init(); 
        // Inizializzazione del DAO per l'interazione con il database
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Recupera la sessione, non creare se non esiste
        
        if (session != null) {
            session.invalidate(); // Invalida la sessione se esiste
        }
        
        // Redirige alla pagina di login
        response.sendRedirect("login.jsp");
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
