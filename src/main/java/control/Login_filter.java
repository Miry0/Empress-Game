package control; 

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebFilter("/UserProfileServlet") // Applica questo filtro alla servlet UserProfileServlet
public class Login_filter extends HttpFilter implements Filter  {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	super.init();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;	
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Verifica se l'utente è loggato
        if (session != null && session.getAttribute("utente") != null) { //se ci sta una sessione attiva e l'utente non è ==null, allora vuol dire che ha effettuato il login
            // L'utente è loggato, continua la catena di filtri/servlet
            chain.doFilter(request, response); //chain permette alla request e alla response di continuare il loro percorso
        } else {//se l'utente non ha una sessione attiva o è ==null, allora lo reidirizziamo alla pagina di login
            // L'utente non è loggato, reindirizza alla pagina di login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/Pagina_login.jsp");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
