<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.Utenti_bean" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%
  
	String tipoUtente = (String) session.getAttribute("tipoUtente"); //recupera il tipo del utente
	
	// Controlla se l'utente è già registrato e loggato
    if (utente != null && utente.get_nome_utente() != null && !utente.get_nome_utente().isEmpty()) {
    	if ("admin".equals(tipoUtente)) {
    	response.sendRedirect("Profilo_admin.jsp");
    	}
    	else{
    	response.sendRedirect("Profilo_utente.jsp");
    	}
    }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Empress Game- Registrazione</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/style.css">


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
  <a href="<%= contextPath %>/">Home</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<div class="login-form">
 <div class="login-container">
<form id=registrazione_form onsubmit="return validateForm('registrazione_form', ['nome', 'cognome'], ['g_nascita', 'm_nascita', 'a_nascita'], null)" action="${pageContext.request.contextPath}/Registrazione_servlet"  method="post">
	
		<h5>Dati personali</h5>
			<input type="text" name="nome" placeholder="nome" class="formInput" required autofocus>
			<input type="text" name="cognome" placeholder="cognome" class="formInput" required>
			<input type="text" name="g_nascita" placeholder="giorno nascita 30" class="formInput" required>
			<input type="text" name="m_nascita" placeholder="mese nascita 07" class="formInput" required>
			<input type="text" name="a_nascita" placeholder="annno nascita 1987" class="formInput" required>
			
	
	<h5>Dati di login</h5>
	<input type="text" name="nome_utente" placeholder="nome utente" class="formInput" required>
	<input type="text" name="email" placeholder="email" class="formInput" required>
	<input type="password" name="pass" placeholder="password" class="formInput" required>
	
	 <span id="passwordError" class="error-message"></span>
	
	<!-- quando preme su invia, viene rimandato sulla home -->
	<input type="submit" name="invio" value="Invia" class="button1">
	<input type="reset" name="reset" value="Reset" class="button1" >
</form>
</div>
</div>
<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>