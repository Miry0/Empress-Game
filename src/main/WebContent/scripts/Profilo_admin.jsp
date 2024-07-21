<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.Utenti_bean" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<%
    // Recupera l'oggetto utente dalla sessione
    utente = (Utenti_bean) session.getAttribute("utente");

    // Se l'utente non è loggato, reindirizza alla pagina di login
    if (utente == null) {
    	  RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Profilo Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css"> 
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_barra_ricerca.css">
</head>
<body>

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
</div>

<!-- tasto per carrello e profilo -->
<div class="header-right">
    <div>
        <a href="<%= contextPath %>/scripts/Gestione_carrello.jsp">
            <img src="<%= contextPath %>/images/cart-icon.png" alt="Carrello">
        </a>
    </div>
    </div>
    
  <!-- Box laterale con i bottoni -->
<div class="sidebar">

      <form action="${pageContext.request.contextPath}/lista_desideri_servlet" method="post">
        <input type="hidden" name="nome_utente_lista" value="<%= utente.get_nome_utente() %>">     
        <button name="lista_desideri" type="submit" value="view">Lista Desideri</button>
      </form>
</div>
    
<div class="login-form">
    <div class="login-container">
        <h5>Profilo Admin</h5>
        <form id="profilo_form" action="Login_servlet" method="post" onsubmit="return validateForm('profilo_form', ['nome', 'cognome'], ['g_nascita'. 'm_nascita', 'a_nascita'], null);">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="<%= utente.get_nome() %>"><br>

            <label for="cognome">Cognome:</label>
            <input type="text" id="cognome" name="cognome" value="<%= utente.get_cognome() %>"><br>
            
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" value="<%= utente.get_email() %>"><br>
            
            <label for="g_nascita">Giorno di nascita:</label>
            <input type="text" id="g_nascita" name="g_nascita" value="<%= utente.get_g_nascita() %>"><br>
            
            <label for="m_nascita">Mese di nascita:</label>
            <input type="text" id="m_nascita" name="m_nascita" value="<%= utente.get_m_nascita() %>"><br>
            
            <label for="a_nascita">Anno di nascita:</label>
            <input type="text" id="a_nascita" name="a_nascita" value="<%= utente.get_a_nascita() %>"><br>
            
        </form>
        
          <!-- Link per il logout -->
        <form action="${pageContext.request.contextPath}/Logout_servlet" method="post">
            <button type="submit">Logout</button> <!-- gestito dalla Logout_servlet -->
        </form>
           
        <!-- Pulsanti per lo storico e la gestione del catalogo -->
        <form action="${pageContext.request.contextPath}/Storico_servlet" method="post">
         <input type="hidden" name="nome_utente_ordine" value="<%= utente.get_nome_utente() %>">  
            <button name=confema_ordine type="submit" value="view" >Storico</button> <!-- Reindirizza alla servlet per lo storico -->
        </form>
        
        <form action="<%= contextPath %>/scripts/Gestione_catalogo.jsp" method="post">
            <button type="submit">Gestione Catalogo</button> <!-- Reindirizza alla servlet per la gestione del catalogo -->
        </form>
    </div>
</div>

<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>
</html>
