<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_barra_ricerca.css">
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
  <a href="<%= contextPath %>/scripts/Profilo_utente.jsp" id="profile-link">Profilo</a>
  <a href="<%= contextPath %>/scripts/Registrazione.jsp" id="profile-link">Registrati</a>
  <a href="<%= contextPath %>/scripts/Pagina_login.jsp" id="profile-link">Login</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<!-- Barra di ricerca -->
<div class="search-container">
  <form action="<%= contextPath %>/RicercaServlet" method="post">
    <div class="search-form">
      <input type="text" placeholder="Cerca giochi per nome..." name="nomeGioco" class="search-input">
      <button type="submit" class="search-button">Cerca</button>
    </div>
  </form>
</div>

<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>

</body>
</html>

