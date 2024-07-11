package control;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Utenti_DAODataSource;
import model.Utenti_bean;

//@WebServlet("/Login_servlet")

public class Logout_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Utenti_DAODataSource utenti;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
       // gameDAO = new Game_DAODataSource(getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
        utenti = new Utenti_DAODataSource(ds);
    	HttpSession session = request.getSession(false); // Recupera la sessione, non creare se non esiste
        
        if (session != null) {
            session.invalidate(); // Invalida la sessione se esiste
        }
        
        // Redirige alla pagina di login
        request.getRequestDispatcher("index.jsp").forward(request, response);   
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gestione GET, redirige alla pagina di login
    	request.getRequestDispatcher("index.jsp").forward(request, response); 
    }
    
    public void destroy() {
    	super.destroy(); 
        // Chiusura risorse se necessario
    }
   
}
