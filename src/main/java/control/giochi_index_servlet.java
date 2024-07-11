package control; 

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Game_DAODataSource;
import model.Game_bean;

@WebServlet(name = "StartupServlet", urlPatterns = { "/StartupServlet" }, loadOnStartup = 1)
public class giochi_index_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Game_DAODataSource gameDAO;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        gameDAO = new Game_DAODataSource(ds);

        try {
            //List<Game_bean> listaGiochi3 = gameDAO.Mostra_tutto();
        	
        	Collection <Game_bean>listaGiochi3=gameDAO.Mostra_tutto(); //richiamiamo il metodo Mostra_tutto() e salviamo i dati nella lista
            request.setAttribute("listaGiochi3", listaGiochi3); //rettiamo la lista come attributo della request in modo che possa essere recuperato dalla jsp
            request.getRequestDispatcher("scripts/index.jsp").forward(request, response); //gestiamo i risultati nella jsp designata

        } catch (SQLException e) {
            e.printStackTrace();
            // Gestione dell'errore nel recupero dei giochi dal database
            // Puoi registrare l'errore nei log o in un altro modo
        }
    }
    
    public void destroy() {
        super.destroy();
    }
   
}
