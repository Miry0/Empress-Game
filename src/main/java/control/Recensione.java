package control; 
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.org.

import model.Recensioni_bean;
import model.Recensioni_DAODataSource;

@WebServlet("/Recensione")
public class Recensione extends HttpServlet {
	  private static final long serialVersionUID = 1L;
	  
	  private Recensioni_DAODataSource recensioneDAO;

	  public void init(ServletConfig cfg) throws ServletException {
	        super.init(cfg);
	        // Inizializzazione del DAO per interagire con il database dei giochi
	       // gameDAO = new Game_DAODataSource(getServletContext());
	    }
	    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
        recensioneDAO = new Recensioni_DAODataSource(ds);
        
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lettura del corpo della richiesta
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();

        // Parsing del JSON ricevuto
        JSONObject jsonRequest = new JSONObject(jsonString);
        String chiave1 = jsonRequest.getString("chiave1");
        String chiave2 = jsonRequest.getString("chiave2");

        // Creazione della risposta JSON
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("risposta1", "valoreRisposta1");
        jsonResponse.put("risposta2", "valoreRisposta2");

        // Impostazione del tipo di contenuto della risposta
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Scrittura della risposta
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}
    
    public void destroy() {
        super.destroy();
        // Eventuale chiusura risorse
    }
}
