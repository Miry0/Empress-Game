package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import model.Carrello;
import model.Game_DAODataSource;
import model.Game_bean;

@WebServlet("/CarrelloServlet")
public class Carrello_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Game_DAODataSource gameDAO;

    public void init(ServletConfig cfg) throws ServletException {
        super.init(cfg);
        // Inizializzazione del DAO per interagire con il database dei giochi
        // gameDAO = new Game_DAODataSource(getServletContext());
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource) getServletContext().getAttribute("MyDataSource");
        gameDAO = new Game_DAODataSource(ds);
        HttpSession session = request.getSession();
        
        Carrello carrello=(Carrello) session.getAttribute("carrello"); //recuperiamo il carrello che in cui proviamo ad aggiungere nei metodi "Add" e "remove" sotto 
        boolean iscartCreated=(boolean) session.getAttribute("isCartCreated"); //reucperiamo lo stato del carrello dalla servlet del login
        System.out.print(" iscartCreated " + iscartCreated);
      /*  if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }*/
        
        System.out.print(" carrello 1  " + carrello);

        if(iscartCreated== false) {
        	 carrello= (Carrello) new Carrello(); 
        	 session.setAttribute("carrello", carrello);
             session.setAttribute("isCartCreated", true); // Impostiamo che il carrello è stato creato
        }
        else {
        	 carrello = (Carrello) session.getAttribute("carrello");
        	//session.setAttribute("carrello", carrello);
        }
        
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }
        
        System.out.print(" carrello 2  " + carrello);

        String action = request.getParameter("azione_carrello");
        System.out.print(" action  " + action);

        
        if (action != null) {
            switch (action) {
                case "aggiungi":
                    aggiungiElemento(request, response, carrello);
                    break;
                case "elimina":
                    eliminaElemento(request, response, carrello);
                    break;
                default:
                    mostraCarrello(request, response, carrello);
                    break;
            }
        } else {
            mostraCarrello(request, response, carrello);
        }
    }

    private void mostraCarrello(HttpServletRequest request, HttpServletResponse response, Carrello carrello)
            throws ServletException, IOException {
        Collection<Game_bean> catalogo = new LinkedList<>();
        
        for (int i = 0; i < carrello.getCarrelloLenght(); i++) {
            int idGioco = carrello.getGiocoByIndex(i);
            Game_bean gioco = null;
            
            try {
                gioco = gameDAO.doRetrieveByKey(idGioco);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            if (gioco != null) {
                catalogo.add(gioco);
            }
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("catalogo", catalogo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/scripts/Gestione_carrello.jsp");
        dispatcher.forward(request, response);
    }

    private void aggiungiElemento(HttpServletRequest request, HttpServletResponse response, Carrello carrello)
            throws ServletException, IOException {
    	
        int idGioco = Integer.parseInt(request.getParameter("aggiungi_carrello"));
        
             
            	
        // Se l'ID non è presente nel carrello, aggiungi il gioco
        carrello.aggiungiGioco(idGioco);
        HttpSession session = request.getSession();
        session.setAttribute("carrello", carrello);
        mostraCarrello(request, response, carrello);
       
    }

    private void eliminaElemento(HttpServletRequest request, HttpServletResponse response, Carrello carrello)
            throws ServletException, IOException {
        int idGioco = Integer.parseInt(request.getParameter("elimina_carrello"));
        System.out.println("idGioco da eliminare " + idGioco);
        carrello.removeGiocoByKey(idGioco);

        HttpSession session = request.getSession();
        session.setAttribute("carrello", carrello);
        mostraCarrello(request, response, carrello);
    }


    public void destroy() {
        super.destroy();
    }
}
