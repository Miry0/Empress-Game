<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Empress Game</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<title>Empress Games - Catalogo Giochi</title>
 	<!-- va aggiunto un foglio di stile -->
    <!-- Includi qui i tuoi stili CSS se necessario -->
</head>
<body>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
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

<c:if test="${not empty risultatiRicerca}"> <!-- se la ricerca ha dato dei risultati -->
    <ul>
        <c:forEach var="game" items="${risultatiRicerca}">
            <li>${game.immagine}- ${game.nome} - ${game.piattaforma} - ${game.genere} - ${game.prezzo}</li> 
            <!-- inseriamo i campi che vogliamo far mostrare -->
        </c:forEach>
    </ul>
<c:else> 
    <p>Nessun risultato trovato per la ricerca: <strong>${param.searchQuery}</strong></p>
    <!-- se la ricerca non è andata a buon fine. "String" contiene tutti i parametri della richiesta http e "param" rappresenta uno string -->
    </c:else>
</c:if>


<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>
</html>



