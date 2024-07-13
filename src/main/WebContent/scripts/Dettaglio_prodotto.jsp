<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.Utenti_bean" %>
<%@ page import="model.Game_bean" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>

<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games </title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css"> 
</head>
<body>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
	Game_bean gioco = (Game_bean) request.getAttribute("gioco");
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
  <!-- Link per il logout -->
        <form action="Logout_servlet" method="post">
            <button type="submit">Logout</button> <!-- gestito dalla Logout_servlet -->
        </form>
</div>

<!-- Contenuto principale della pagina -->
        <% if (gioco != null) { %>
            <div class="game-details">
                <h2><%= gioco.get_nome() %></h2>
                <p>Piattaforma: <%= gioco.get_piattaforma() %></p>
                <p>Genere: <%= gioco.get_genere() %></p>
                <p>Prezzo: <%= gioco.get_prezzo() %>€</p>
                <p>Data di uscita: <%= gioco.get_g_uscita() %>-<%= gioco.get_m_uscita() %>-<%= gioco.get_a_uscita() %></p>
                <% if (gioco.getImmagine() != null) { %>
                    <img src="data:image/jpeg;base64,<%= new String(gioco.getImmagine()) %>" alt="<%= gioco.get_nome() %>">
                <% } %>
               
              
            </div>
        <% } else { %>
            <p>Gioco non trovato.</p>
        <% } %>
        
        <!-- Form per aggiungere il gioco al carrello -->
        <!-- nella ridirezione alla servlet, mandiamo anche l'id del gioco  -->
      <form action="${pageContext.request.contextPath}/Carrello_servlet?id_gioco=<%= gioco.get_id_gioco() %>" method="post">
        <input type="hidden" name="aggiungi_carrello" value="<%= gioco.get_id_gioco() %>">
        <button name="azione_carrello" value="aggiungi" type="submit">Aggiungi al carrello</button>
      </form>
      
      <!-- Form per aggiungere il gioco alla lista desideri -->
      <form action="${pageContext.request.contextPath}/lista_desideri_servlet?id_gioco=<%= gioco.get_id_gioco() %>" method="post">
        <input type="hidden" name="aggiungi_lista" value="<%= gioco.get_id_gioco() %>">
        <button type="submit">Aggiungi alla lista desideri</button>
      </form>
        
<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>
 </body>
 </html>