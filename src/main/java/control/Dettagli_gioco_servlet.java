package control; 

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

import model.Game_bean;
import model.Game_DAODataSource;

//@WebServlet("/Dettagli_gioco_servlet")
public class Dettagli_gioco_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
   
    private Game_DAODataSource gameDAO;


    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
    }
    

    // Gestisce le richieste GET
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera il parametro 'nome' dalla query string
    	 DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
         gameDAO = new Game_DAODataSource(ds);
         
        String id = request.getParameter("id_gioco");//recuperiamo l'id del gioco di cui si richiede la scheda dei dettagli 
        if (id != null && !id.isEmpty()) {
          
        	try {
                int int_id = Integer.parseInt(id); //convertiamo l'id in intero (cioè lo stesso tipo con cui è salvato nel db)

                Game_bean gioco= gameDAO.doRetrieveByKey(int_id); // se l'id corrisponde ad un gioco, allora ci resitutisce tutti i suoi dettagli 
                
                if (gioco != null) { //se la ricerca ci ha restituito qualcosa, allora il gioco esiste
                	
                 
                            // Imposta l'attributo 'gioco' (che abbiamo recuperato) nella richiesta
                            request.setAttribute("gioco", gioco);
                            // Inoltra la richiesta alla JSP dei dettagli del gioco
                            request.getRequestDispatcher("/scripts/Dettaglio_prodotto.jsp").forward(request, response);
                            return;
                        }
		            else {
		                // Se non trova il gioco, reindirizza alla home page
                        request.getRequestDispatcher("/").forward(request, response); //riportiamo sul WebContent se non ci sta il gioco con l'id ricercato
		            }
        } catch (NumberFormatException | SQLException e) {
            // Gestisce l'eccezione se l'ID non è un numero valido
            System.out.println("Errore nel recupero dei dettagli del gioco");
            e.printStackTrace();
            // Reindirizza alla home page in caso di errore di formato
           // response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
            
     }
}

    
    // Distrugge la servlet e pulisce le risorse se necessario
    public void destroy() {
        super.destroy();
    }
}
