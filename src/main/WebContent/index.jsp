<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.Collection" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_barra_ricerca.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_carrello_profilo.css">

  
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
<body>

<!-- tasto per carrello e profilo -->
<div class="header-right">
  
<div onclick="location.href='<%= contextPath %>/scripts/Gestione_carrello.jsp'">
      <img src="<%= contextPath %>/images/cart-icon.png" alt="Carrello">
    </div>
    <div>
    <a id="profile-link" href="<%= contextPath %>/scripts/Profilo_utente.jsp" >
      <img src="<%= contextPath %>/images/user-icon.png" alt="Profilo">
    </a>
    </div>
 </div>
   
<!-- Bottone per attivare il menu -->
<div class="toggle-btn" onclick="toggleMenu()"></div>

<!-- Menu a comparsa -->
<div id="menu" class="menu">
  <span class="close-icon" onclick="toggleMenu()">X</span> <!-- Icona di chiusura -->
  <a href="<%= contextPath %>/scripts/Registrazione.jsp" id="profile-link">Registrati</a>
  <a href="<%= contextPath %>/scripts/Pagina_login.jsp" id="profile-link">Login</a>
  <a href="#">Impostazioni</a>
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

<!-- Barra di ricerca -->
<div class="search-container">
  <form action="<%= contextPath %>/scripts/Risultati_ricerca.jsp" method="post">
    <div class="search-form">
      <input type="text" placeholder="Cerca giochi per nome..." name="nomeGioco" class="search-input">
      <button type="submit" class="search-button">Cerca</button>
    </div>
  </form>
</div>

<h1>Catalogo Giochi</h1>

<!-- Form per selezionare l'ordinamento -->
<form action="Gestione_giochi_servlet" method="get">
    <label for="order">Ordina per:</label>
    <select name="order" id="order">
        <option value="nome">Nome</option>
        <option value="prezzo">Prezzo</option>
        <option value="genere">Genere</option>
        <option value="piattaforma">Piattaforma</option>
    </select>
    <input type="submit" value="Ordina">
</form>

<!-- Codice per visualizzare la lista dei giochi -->
<%
    Collection<Game_bean> games = (Collection<Game_bean>) request.getAttribute("listaGiochi");
    if (games != null && !games.isEmpty()) {
        for (Game_bean game : games) {
%>
    <div>
        <h2><%= game.get_nome() %></h2>
        <p>Piattaforma: <%= game.get_piattaforma() %></p>
        <p>Genere: <%= game.get_genere() %></p>
        <p>Prezzo: <%= game.get_prezzo() %></p>
        <p>Data di uscita: <%= game.get_g_uscita() %>-<%= game.get_m_uscita() %>-<%= game.get_a_uscita() %></p>
        <% if (game.getImmagine() != null) { %>
        <img src="data:image/jpeg;base64,<%= new String(game.getImmagine()) %>" alt="<%= game.get_nome() %>">
        <% } %>
    </div>
<%
        }
    } else {
%>
    <p>Nessun gioco disponibile.</p>
<%
    }
%>

<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>

</body>
</html>
