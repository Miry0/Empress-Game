<<<<<<< HEAD
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
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
  <a href="<%= contextPath %>scripts/Profilo_utente.jsp" id="profile-link">Profilo</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>

</body>
</html>
=======
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
</head>
<body>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
%>
<p>Context Path: <%= contextPath %></p>

<!-- Header con logo -->
<header>
  <img src="<%= contextPath %>/images/logo.jpg" alt="Logo">
</header>

<!-- Bottone per attivare il menu -->
<div class="toggle-btn" onclick="toggleMenu()"></div>

<!-- Menu a comparsa -->
<div id="menu" class="menu">
  <span class="close-icon" onclick="toggleMenu()">X</span> <!-- Icona di chiusura -->
  <a href="<%= contextPath %>/profilo.html" id="profile-link">Profilo</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>

</body>
</html>
>>>>>>> refs/remotes/origin/master
