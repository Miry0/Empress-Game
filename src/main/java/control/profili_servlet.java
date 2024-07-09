package control; 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import model.Utenti_bean;
import model.Utenti_DAODataSource;

//@WebServlet("/UserProfileServlet")
public class profili_servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
