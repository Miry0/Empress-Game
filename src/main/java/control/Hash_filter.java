//filtro che si occupa di calcolare l'hash della password e di salvarla nel db (usiamo SHA-256); 
package control; 

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.HttpConstraintElement.*; 

import model.Utenti_DAODataSource;
import model.Utenti_bean;

//questa prima riga la eliminiamo e configuriamo il filtro sulle varie servlet tramite il fil web.xml
//@WebFilter("urlPatterns = {\"/Registrazione_servlet/*\", \"/Altra_servlet/*\"}")//il filtro viene applicato a tutte le richieste che corrispondo a  
public class Hash_filter extends HttpFilter implements Filter {
	
	  //sovrascriviamo l'inizializzazione del filtro
	    public void init(FilterConfig filterConfig) throws ServletException {
	        // Inizializzazione del filtro se necessario
	    	super.init(); 
	    }
	 
	 @Override
	    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        if (request instanceof HttpServletRequest) {
	            HttpServletRequest httpRequest = (HttpServletRequest) request;
	            String password = httpRequest.getParameter("password");

	            if (password != null) {
	                // Esegue l'hashing della password
	                String hashedPassword = hash(password);

	                // Wrappa la richiesta per sostituire la password presa dalla request con l'hash alcolato con la funzione "hash"
	                HttpServletRequest wrappedRequest = new FilterRequestWrapper(httpRequest, hashedPassword);
	                chain.doFilter(wrappedRequest, response); // Passa la richiesta modificata
	                return;
	            }
	        }
	        chain.doFilter(request, response); // Passa la richiesta originale ai prossimo filtri o alla servlet di destinazione. 
	    }

	  //sovrascriviamo il metodo di distruzione del filtro
	    public void destroy() {
	    	super.destroy(); 
	        
	    }
	 
	 // Metodo per eseguire l'hashing di una password in formato stringa utilizzando SHA-256
	    private String hash(String data) {
	        try {
	        	//MessageDigest è fornita da una classe java e si occupa dell'hasgind delle password
	            MessageDigest digest = MessageDigest.getInstance("SHA-256");
	            byte[] encodedhash = digest.digest(data.getBytes("UTF-8")); 
	            return bytesToHex(encodedhash);
	        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) { //algortimo di codofica non disponibile e codifica non supportata
	            throw new RuntimeException(e);
	        }
	    }

	    // Metodo per convertire un array di byte in una stringa esadecimale
	    private String bytesToHex(byte[] hash) {
	        StringBuilder hexString = new StringBuilder(2 * hash.length);
	        for (byte b : hash) { //scorriamo byte per byte tutto l'hash 
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    }
	    
	    private static class FilterRequestWrapper extends HttpServletRequestWrapper {
	        private final String hashedPassword;

	        public FilterRequestWrapper(HttpServletRequest request, String hashedPassword) {
	            super(request);
	            this.hashedPassword = hashedPassword;
	        }

	        
	        public String getParameter(String name) {
	            if ("password".equals(name)) {
	                return hashedPassword;
	            }
	            return getParameter(name);
	        }
	    }
	
}






