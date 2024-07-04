<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.Utenti_bean" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%
    // Controlla se l'utente è già loggato
    if (utente != null && utente.get_nome_utente() != null && !utente.get_nome_utente().isEmpty()) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

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
  <a href="<%= contextPath %>/scripts/Profilo_utente.jsp" id="profile-link">Profilo</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<!-- Form di login -->
<div class="login-form">
  <div class="login-container">
    <form action="<%= contextPath %>/Login_servlet" method="POST">
      <input type="text" name="username" placeholder="Username" required>
      <input type="password" name="password" placeholder="Password" required>
      <button type="submit">Login</button>
    </form>
  </div>
</div>

<script src="<%= contextPath %>/script_index.js"></script>

</body>
</html>
