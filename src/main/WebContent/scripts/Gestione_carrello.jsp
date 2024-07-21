<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Carrello" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.Collection" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<jsp:useBean id="carrello" class="model.Carrello" scope="session"/>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empress Game - Carrello</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
</head>
<body>

<%
    String contextPath = request.getContextPath();


    // Controlla se l'utente è già loggato
    if (utente == null || utente.get_nome_utente() == null || utente.get_nome_utente().isEmpty()) {
        response.sendRedirect("Pagina_login.jsp");
        return;
    }

    Collection<Game_bean> games = (Collection<Game_bean>) request.getAttribute("listaGiochi"); //recuperiamo la lista intera dei giochi
    //ArrayList<Game_bean> prodotti = new ArrayList<Game_bean>(); //creiamoci un array list dove poter memorizzare l'intera descrizione dei giochi
//    ArrayList<Integer> catalogo = (ArrayList<Integer>)session.getAttribute("catologo"); //recuperiamo l'id dei giochi nel carrello
   
	//recuperiamo il carrello dalla sessione
	carrello= (Carrello) session.getAttribute("carrello"); //recuperiamoci gli id che stanno nel carrello
    
 // Recupera il catalogo dalla sessione
    Collection<Game_bean> catalogo = (Collection<Game_bean>) session.getAttribute("catalogo");
    
    float totale=0; 
 // Calcolo del totale
    if (catalogo != null) {
        for (Game_bean prodotto : catalogo) {
            int quantita = carrello.getQuant(prodotto.get_id_gioco());
            totale += prodotto.get_prezzo() * quantita;
        }
    }
    
%>

<!-- Header con logo -->
<header>
  <img src="<%= contextPath %>/images/logo.jpg" alt="Logo">
</header>

<!-- Bottone per attivare il menu -->
<div class="toggle-btn" onclick="toggleMenu()"></div>

<!-- Menu a comparsa -->
<div id="menu" class="menu">
  <span class="close-icon" onclick="toggleMenu()">X</span> <!-- Icona di chiusura -->
  <a href="<%= contextPath %>/">Home</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<div class="container">
    <% if (catalogo != null && !catalogo.isEmpty()) { %>
        <table>
            <thead>
                <tr>
                    <th>Nome Prodotto</th>
                    <th>Prezzo</th>
                    <th>Quantità</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <% for (Game_bean prodotto : catalogo) { %>
                    <tr>
                        <td><%= prodotto.get_nome() %></td>
                        <td><%= prodotto.get_prezzo() %> €</td>
                        <td><%= carrello.getQuant(prodotto.get_id_gioco()) %></td>
                        <td>
                            <form action="${pageContext.request.contextPath}/CarrelloServlet" method="post">
                                <input type="hidden" name="azione_carrello" value="elimina">
                                <input type="hidden" name="elimina_carrello" value="<%= prodotto.get_id_gioco() %>">
                                <button name="azione_carrello" value="elimina" type="submit">Elimina</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
                 <tr>
                    <td colspan="4" style="text-align: right;"><strong>Totale: <%= totale %> €</strong></td>
                </tr>
            </tbody>
        </table>
    <% } else { %>
        <p>Il carrello è vuoto.</p>
    <% } %>
</div>


<% if (catalogo != null && !catalogo.isEmpty()) { %>	
<form action="${pageContext.request.contextPath}/Storico_servlet" method="post">
 <input type="hidden" name="carrello" value="<%=carrello %>"> <!-- contiene id_gioco e quantità dei giochi comprati --> 
 <input type="hidden" name="nome_utente" value="<%=utente.get_nome_utente() %>">
 <input type="hidden" name="totale" value="<%=totale%>">
   
  <button name="confema_ordine" type="submit" value="conferma"> Conferma l'ordine</button> 
</form>
<%}%>

<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>

</body>
</html>
