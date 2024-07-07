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
  <title>Empress Games - Login</title>
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
  <a href="<%= contextPath %>/index.jsp">Home</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<!-- Form di login -->
<div class="login-form">
  <div class="login-container">
    <form id="loginForm" action="<%= contextPath %>/Login_servlet" method="POST" onsubmit="return validateForm()">
      <input type="text" name="username" id="username" placeholder="Username">
      <span id="usernameError" class="error-message"></span>
      <input type="password" name="password" id="password" placeholder="Password">
      <span id="passwordError" class="error-message"></span>
      <button type="submit" >Login</button> <!-- gestito dalla Login_servlet -->
    </form>
    
    <!-- Link per la registrazione -->
    <p>Non hai un account? <a class="link1" href="<%= contextPath %>/scripts/Registrazione.jsp">Registrati qui </a></p>

  </div>
</div>


<script src="<%= contextPath %>/scripts/script_index.js"></script>

</body>
</html>
