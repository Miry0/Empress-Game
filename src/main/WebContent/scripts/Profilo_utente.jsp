<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.Utenti_bean" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%


    // Recupera l'oggetto utente dalla sessione
    	utente = (Utenti_bean) session.getAttribute("utente");
    // Se l'utente non è loggato, reindirizza alla pagina di login
    if (utente == null) {
        response.sendRedirect("Pagina_login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games - Profilo Utente</title>
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
 	
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>
	
<div class="login-form">
  	<div class="login-container">
    <h5>Profilo Utente</h5>
    <form id=profilo_form action="<%= contextPath %>/Login_servlet" method="post" onsubmit="return validateForm('profilo_form', ['nome', 'cognome']);">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="<%= utente.get_nome() %>"><br>

        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" value="<%= utente.get_cognome() %>"><br>
		
		<label for="password">Password:</label>
        <input type="text" id="password" name="password" value="<%= utente.get_password() %>"><br>
        
		<label for="email">Email:</label>
        <input type="text" id="email" name="email" value="<%= utente.get_email() %>"><br>
        
        <label for="g_nascita">Giorno di nascita:</label>
        <input type="text" id="g_nascita" name="g_nascita" value="<%= utente.get_g_nascita() %>"><br>
        
		<label for="m_nascita">Mese di nascita:</label>
        <input type="text" id="m_nascita" name="m_nascita" value="<%= utente.get_m_nascita() %>"><br>
        
        <label for="a_nascita">Anno di nascita:</label>
        <input type="text" id="a_nascita" name="a_nascita" value="<%= utente.get_a_nascita() %>"><br>
        
        <span id="passwordError" class="error-message"></span>
        <button type="submit">Aggiorna Profilo</button> <!-- l'update di un profilo è gestito dalla login servlet -->
    </form>
    
    <!-- Link per il logout -->
    <button type="logout" href="Logout_servlet">Logout</button> <!-- gestito dalla Logout_servlet -->
   </div>
   
</div>
<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>
</html>