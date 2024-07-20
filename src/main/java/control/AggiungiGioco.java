package control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import model.Game_bean;
import model.Game_DAODataSource;

/**
 * Servlet implementation class AddGame
 */
@WebServlet("/AggiungiGioco")
@MultipartConfig()
public class AggiungiGioco extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String SAVE_DIR = "images";
	static Game_DAODataSource gameDAO;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");

		out.write("Error: GET method is used but POST method is required");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Collection<?> games = (Collection<?>) request.getAttribute("listaGiochi");
		
		DataSource ds=(DataSource) getServletContext().getAttribute("MyDataSource");  
        gameDAO = new Game_DAODataSource(ds);
        
		String savePath = request.getServletContext().getRealPath("") + File.separator + SAVE_DIR;
		Game_bean g1 = new Game_bean();
		
		String fileName= null;
		if (request.getParts() != null && request.getParts().size() > 0) {
			for (Part part : request.getParts()) {
				fileName = extractFileName(part);
			
				if (fileName != null && !fileName.equals("")) {
					part.write(savePath + File.separator + fileName);
					g1.setImmagine(fileName);
				}
					
			}
		}
		
		g1.set_nome(request.getParameter("nome"));
		g1.set_a_uscita((Integer.valueOf(request.getParameter("a_uscita"))));
		g1.set_m_uscita((Integer.valueOf(request.getParameter("m_uscita"))));
		g1.set_g_uscita((Integer.valueOf(request.getParameter("g_uscita"))));
		g1.set_piattaforma(request.getParameter("piattaforma"));
		//g1.set_quantita(Integer.valueOf(request.getParameter("quantita")));
		g1.set_quantita(10);
		g1.set_genere(request.getParameter("genere"));
		g1.set_prezzo(Float.valueOf(request.getParameter("prezzo")));
		
		try {
			gameDAO.doSave(g1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//request.setAttribute("message", message);
		//request.setAttribute("stato", "success!"); FORSE DA IMPLEMENTARE
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/scripts/Gestione_catalogo.jsp");
		dispatcher.forward(request, response);
	}
	private String extractFileName(Part part) {
		// content-disposition: form-data; name="file"; filename="file.txt"
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}
	

}
