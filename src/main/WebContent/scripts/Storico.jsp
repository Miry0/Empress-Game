<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  import="model.Utenti_bean"%>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="model.Storico_bean" %>
<%@ page import="model.ArticoloBean" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.Collection" %>


<%
    // Recupera l'oggetto utente dalla sessione
    utente=(Utenti_bean) session.getAttribute("utente"); 

	String nome_utente= utente.get_nome_utente(); //recuperiamo il nome utente
	String tipoUtente= utente.get_tipo(); //recuperiamo il tipo dell'utente; 
	
	Collection<Storico_bean> storico= (Collection<Storico_bean>) request.getAttribute("storico");
	Collection<ArticoloBean> articoli= (Collection<ArticoloBean>) request.getAttribute("articoli");
	Collection<Game_bean> games= (Collection<Game_bean>) request.getAttribute("listaGiochi");
	
	

    
%>

<!DOCTYPE html>
<html>
<head>
    <title>Empress Game - Storico degli Ordini</title>
</head>
<body>

<div>
    <% if ("admin".equals(tipoUtente)) {
        if (storico.isEmpty()) { %>
            <p>Nessun ordine effettuato</p>
        <% } else {
            for (Storico_bean st : storico) { %>
                <div>
                    <h2>N.ordine: <%= st.get_n_ordine() %> Totale: <%= st.get_totale() %></h2>
                    <% for (ArticoloBean art : articoli) {
                        if (art.getnOrdine() == st.get_n_ordine()) {
                            for (Game_bean game : games) {
                                if (art.get_id_gioco() == game.get_id_gioco()) { %>
                                	<p>Nome utente: <%= st.get_nome_utente() %></p>
                                    <p>Nome gioco: <%= game.get_nome() %></p>
                                    <p>Genere: <%= game.get_genere() %></p>
                                    <p>Prezzo: <%= game.get_prezzo() %></p>
                                    <p>Quantità: <%= art.get_quantita() %></p>
                                <% } %>
                            <% } %>
                        <% } %>
                    <% } %>
                </div>
            <% } %>
        <% } %>
    <% } else {
        boolean hasOrders = false;		
        for (Storico_bean st : storico) {
            if (st.get_nome_utente().equals(nome_utente)) {
                hasOrders = true; %>
                <div>
                    <h2>N.ordine: <%= st.get_n_ordine() %> Totale: <%= st.get_totale() %></h2>
                    <% for (ArticoloBean art : articoli) {
                        if (art.getnOrdine() == st.get_n_ordine()) {
                            for (Game_bean game : games) {
                                if (art.get_id_gioco() == game.get_id_gioco()) { %>
                                	<p>Nome utente: <%= st.get_nome_utente() %></p>
                                    <p>Nome gioco: <%= game.get_nome() %></p>
                                    <p>Genere: <%= game.get_genere() %></p>
                                    <p>Prezzo: <%= game.get_prezzo() %></p>
                                    <p>Quantità: <%= art.get_quantita() %></p>
                                <% } %>
                            <% } %>
                        <% } %>
                    <% } %>
                </div>
            <% } %>
        <% }
        if (!hasOrders) { %>
            <p>Nessun ordine effettuato</p>
        <% } %>
    <% } %>
</div>



</body>
</html>