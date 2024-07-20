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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_index.css">
  
  
</head>
<body>

<%	
	String tipoUtente = (String) session.getAttribute("tipoUtente"); //recupera il tipo del utente
    Collection<Game_bean> games = (Collection<Game_bean>) request.getAttribute("listaGiochi");
%>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
%>

<!-- Header con logo -->
<header>
  <img src="<%= contextPath %>/images/logo.jpg" alt="Logo">
</header>

<!-- tasto per carrello e profilo -->
<div class="header-right">
    <div>
        <a href="<%= contextPath %>/scripts/Gestione_carrello.jsp">
            <img src="<%= contextPath %>/images/cart-icon.png" alt="Carrello">
        </a>
    </div>
    <div>
        <% if ("admin".equals(tipoUtente)) { %>
            <a href="<%= contextPath %>/scripts/Profilo_admin.jsp">
                <img src="<%= contextPath %>/images/user-icon.png" alt="Profilo Admin">
            </a>
        <% } else if ("base".equals(tipoUtente)) { %>
            <a href="<%= contextPath %>/scripts/Profilo_utente.jsp">
                <img src="<%= contextPath %>/images/user-icon.png" alt="Profilo Utente">
            </a>
        <% } else { %>
            <!-- Gestione caso in cui tipoUtente non è definito o è null -->
            <a href="<%= contextPath %>/scripts/Pagina_login.jsp">
                <img src="<%= contextPath %>/images/user-icon.png" alt="Login">
            </a>
        <% } %>
    </div>
</div>

<!-- Bottone per attivare il menu -->
<div class="toggle-btn" onclick="toggleMenu()"></div>

<!-- Menu a comparsa -->
<div id="menu" class="menu">
    <span class="close-icon" onclick="toggleMenu()">X</span> <!-- Icona di chiusura -->
    <a href="<%= contextPath %>/scripts/Registrazione.jsp" id="registrazione-link">Registrati</a>
    <a href="<%= contextPath %>/scripts/Pagina_login.jsp" id="profile-link">Login</a>
    <a href="#">Impostazioni</a>
    <!-- Link per il logout -->
    <form action="Logout_servlet" method="post">
        <button type="submit">Logout</button> <!-- gestito dalla Logout_servlet -->
    </form>
</div>

<!-- Barra di ricerca -->
<div class="search-container">
    <form action="Gestione_giochi_servlet" method="post" class="search-form">
        <input type="text" placeholder="Cerca giochi per nome..." name="nomeGioco" id="nomeGioco" class="search-input">
        <button type="submit" name="submitAction" value="search" class="search-button">Cerca</button>
    </form>
</div>

<center>
<h3 class="centered-title">Catalogo Giochi</h3>

<!-- Form per selezionare l'ordinamento -->
<div class="order-form-container">
    <form action="Gestione_giochi_servlet" method="get" class="order-form">
        <label for="order">Ordina per:</label>
        <select name="order" id="order">
            <option value="nome">Nome</option>
            <option value="prezzo">Prezzo</option>
            <option value="genere">Genere</option>
            <option value="piattaforma">Piattaforma</option>
        </select>
        <input type="submit" value="Ordina">
    </form>
</div>

</center>
<br>

<div class="games-grid">
    <% if (games != null && !games.isEmpty()) {
        for (Game_bean game : games) { %>
            <div class="game-item">
                <% if (game.getImmagine() != null) { %>
                    <img src="images/<%= game.getImmagine() %>" alt="<%= game.get_nome() %>">
                <% } %> 
                <div>
                    <h2><a href="Dettagli_gioco_servlet?id_gioco=<%= game.get_id_gioco() %>"><%= game.get_nome() %></a></h2>
                    <p>Piattaforma: <%= game.get_piattaforma() %></p>
                    <p>Genere: <%= game.get_genere() %></p>
                    <p>Prezzo: <%= game.get_prezzo() %></p>
                    <p>Data di uscita: <%= game.get_g_uscita() %>-<%= game.get_m_uscita() %>-<%= game.get_a_uscita() %></p>
                </div>
            </div>
    <% } 
       } else { %>
            <div class="game-item">
                <p>Nessun gioco disponibile.</p>
            </div>
    <% } %>
</div>

<!-- Inclusione del file JavaScript -->
<script src="<%= contextPath %>/scripts/script_index.js"></script>

</body>
</html>
