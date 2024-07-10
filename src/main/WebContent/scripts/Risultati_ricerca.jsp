<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Empress Game</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<title>Empress Games - Catalogo Giochi</title>
  	 <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
  	
 	<!-- va aggiunto un foglio di stile -->
    <!-- Includi qui i tuoi stili CSS se necessario -->
</head>
<body>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();;
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
	  <a href="<%= contextPath %>/index.jsp">Home</a>
	  <a href="#">Impostazioni</a>
	  <a href="#">Logout</a>
	  <!-- Aggiungi altre voci del menu qui se necessario -->
	</div>
	
<h2>Risultati della Ricerca</h2>
<% List <Game_bean> listaGiochi = (List <Game_bean>) request.getAttribute("listaGiochi");

if (listaGiochi != null && !listaGiochi.isEmpty()) {
    for (Game_bean game : listaGiochi) {
%>

 <div>
        <h2><%= game.get_nome() %></h2>
        <p>Piattaforma: <%= game.get_piattaforma() %></p>
        <p>Genere: <%= game.get_genere() %></p>
        <p>Prezzo: <%= game.get_prezzo() %></p>
        <p>Data di uscita: <%= game.get_g_uscita() %>-<%= game.get_m_uscita() %>-<%= game.get_a_uscita() %></p>
        <% if (game.getImmagine() != null) { %>
        <img src="data:image/jpeg;base64,<%= new String(game.getImmagine()) %>" alt="<%= game.get_nome() %>">
        <% } %>
    </div>
    <%
  }  
 
    } else {
%>
    <p>Nessun gioco disponibile.</p>
   <%  String message = "la lista è"+ listaGiochi;%>
    <% System.out.println(message);%>
<%
    }
%>


<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>
</html>



