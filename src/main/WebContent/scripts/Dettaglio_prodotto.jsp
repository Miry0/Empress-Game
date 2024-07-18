<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Game_bean" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_dettagli_gioco.css">
  <style>
    .game-container {
      background-color: #f0f0f0; /* Grigio chiaro */
      border-radius: 15px; /* Contorni smussati */
      padding: 20px;
      text-align: center;
      max-width: 600px;
      margin: 20px auto; /* Centra il contenitore nella pagina */
    }
    .game-container img {
      max-width: 100%; /* Limita la larghezza dell'immagine */
      border-radius: 10px;
    }
    .game-details p {
      margin-bottom: 20px; /* Spazio tra i paragrafi */
    }
    .button-container {
      margin-top: 20px;
      display: flex;
      flex-direction: column; /* Disporre i pulsanti uno sotto l'altro */
      align-items: center;
    }
    .button-container button {
      margin: 10px 0; /* Margine sopra e sotto per ogni pulsante */
      padding: 10px 20px;
      border: none;
      background-color: #007BFF;
      color: white;
      border-radius: 5px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }
    .button-container button:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>

<%
    String contextPath = request.getContextPath();
    Game_bean gioco = (Game_bean) request.getAttribute("gioco");
    String messaggioErrore = (String) request.getAttribute("messaggioErrore");
%>

<header>
  <img src="<%= contextPath %>/images/logo.jpg" alt="Logo">
</header>

<div class="toggle-btn" onclick="toggleMenu()"></div>

<div id="menu" class="menu">
  <span class="close-icon" onclick="toggleMenu()">X</span>
  <a href="<%= contextPath %>/">Home</a>
  <a href="#">Impostazioni</a>
  
  <form action="Logout_servlet" method="post">
    <button type="submit">Logout</button>
  </form>
</div>

<div class="container">
  <div class="game-container">
    <h2><%= gioco.get_nome() %></h2>
    <div class="game-details">
      <p>Piattaforma: <%= gioco.get_piattaforma() %></p>
      <p>Genere: <%= gioco.get_genere() %></p>
      <p>Prezzo: <%= gioco.get_prezzo() %>€</p>
      <p>Data di uscita: <%= gioco.get_g_uscita() %>-<%= gioco.get_m_uscita() %>-<%= gioco.get_a_uscita() %></p>
      <% if (gioco.getImmagine() != null) { %>
        <img src="data:image/jpeg;base64,<%= new String(gioco.getImmagine()) %>" alt="<%= gioco.get_nome() %>">
      <% } %>
    </div>
    
    <%-- Visualizza il messaggio di errore se presente --%>
    <% if (messaggioErrore != null && !messaggioErrore.isEmpty()) { %>
      <p class="errore"><%= messaggioErrore %></p>
    <% } %>
    
    <div class="button-container">
      <form action="${pageContext.request.contextPath}/Carrello_servlet?id_gioco=<%= gioco.get_id_gioco() %>" method="post">
        <input type="hidden" name="aggiungi_carrello" value="<%= gioco.get_id_gioco() %>">
        <button name="azione_carrello" value="aggiungi" type="submit">Aggiungi al carrello</button>
      </form>

      <form action="${pageContext.request.contextPath}/lista_desideri_servlet" method="post">
        <input type="hidden" name="gioco_aggiungi_lista" value="<%= gioco.get_id_gioco() %>">
        <input type="hidden" name="nome_utente_lista" value="<%= utente.get_nome_utente() %>">     
        <button name="lista_desideri" type="submit" value="add">Aggiungi alla lista desideri</button>
      </form>
    </div>
  </div>
</div>

<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>
</html>
