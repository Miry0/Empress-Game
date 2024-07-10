package control; 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.IOException;

import model.Utenti_bean;
import model.Sta_nella_lista_DAODataSource;
import model.Utenti_DAODataSource;

//@WebServlet("/UserProfileServlet")
public class profili_servlet extends HttpServlet {
	  private static final long serialVersionUID = 1L;
	  
	  private Utenti_DAODataSource utenti;

	  public void init(ServletConfig cfg) throws ServletException {
	        super.init(cfg);
	        // Inizializzazione del DAO per interagire con il database dei giochi
	       // gameDAO = new Game_DAODataSource(getServletContext());
	    }
	    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
        utenti = new Utenti_DAODataSource(ds);
        
    	HttpSession session = request.getSession(false);
        Utenti_bean utente = (Utenti_bean) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect(request.getContextPath() + "/Pagina_login.jsp");
        } else {
        	String tipo=utente.get_tipo(); //recuperiamo il tipo dell'utente
            request.setAttribute("utente", utente);
            
            if(tipo=="base"){ //se l'utente è tipo base, mostriamo il profilo proposto da Profilo_utente.jsp
            request.getRequestDispatcher("Profilo_utente.jsp").forward(request, response);
            }
            else if(tipo=="admin") {//se l'utente è un admin, lo reidirizziamo a Profilo_admin.jsp, che ha delle funzionalità in più
            request.getRequestDispatcher("Profilo_admin.jsp").forward(request, response);

            }
        }
    }
    
    public void destroy() {
        super.destroy();
        // Eventuale chiusura risorse
    }
}
