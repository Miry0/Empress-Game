package control;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import model.Game_bean;
import model.Game_DAODataSource;

//servlet per permettere che la lista dei giochi sia sempre visibile su index.jsp
public class Mostra_giochi_servlet extends HttpServlet {
	
	private Game_DAODataSource gameDAO;

	    public void init(ServletConfig cfg) throws ServletException {
	        super.init(cfg);
	        /*try {
	        	Collection<Game_bean> gamesList = gameDAO.Mostra_tutto();
	        }catch(SQLException e){
	        	e.printStackTrace();
	        }*/
	    }
	    
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
            gameDAO = new Game_DAODataSource(ds);
            
            try {
                // Carica il catalogo dei giochi dal DAO
                Collection<Game_bean> listaGiochi = gameDAO.Mostra_tutto(); //recuperiamo il catalogo dei giochi dal db

                // Salvaviamo la lista dei giochi nella sessione
                //HttpSession session = request.getSession();
                request.setAttribute("listaGiochi", listaGiochi); //passiamo la lista nella sessione dell'utente

                // Inoltriamo la richiesta alla JSP per mostrare i giochi (index.jsp o la tua homepage)
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Errore durante il recupero dei giochi."); //mostra il catalogo, quando un utente visita la pagina index.jsp
                request.getRequestDispatcher("error.jsp").forward(request, response);
            } 
            
            
           
	    }
                        
	        
}