<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.Utenti_bean" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games - Catalogo Giochi</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css"> 
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_gestione_catalogo.css"> 
  
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

<!-- Form per Aggiungere o Modificare Gioco -->
<div class="form-container">
    <form id="add_form" action="${pageContext.request.contextPath}/AggiungiGioco" method="post" enctype="multipart/form-data" onsubmit="return validateForm('add_form', ['nome', 'piattaforma', 'genere'], ['g_uscita', 'm_uscita', 'a_uscita' ],['prezzo']);">
        <h3>Aggiungi/Modifica Gioco</h3>
        <input type="text" name="nome" placeholder="Nome del gioco">
        <input type="text" name="piattaforma" placeholder="Piattaforma">
        <input type="text" name="genere" placeholder="Genere">
        <input type="text" name="prezzo" placeholder="Prezzo">
        <input type="text" name="g_uscita" placeholder="Giorno di uscita">
        <input type="text" name="m_uscita" placeholder="Mese di uscita">
        <input type="text" name="a_uscita" placeholder="Anno di uscita">
        <input type="file" name="immagine" placeholder="immagine" required accept="images/*"> <!-- Campo per caricare l'immagine del gioco -->
        <button id="agg_button" name="submitAction" type="submit"  value="Aggiungi">Aggiungi</button> <!-- Pulsante per aggiungere un nuovo gioco -->
        <button id="up_butt" name="submitAction" type="submit"  value="Modifica">Modifica</button> <!-- Pulsante per modificare un gioco esistente -->
    </form>
</div>

<!-- Form per Eliminare Gioco -->
<div class="form-container">
    <h3>Elimina Gioco</h3>
    <form id="delete_form" action="${pageContext.request.contextPath}/Gestione_giochi_servlet" method="post" onsubmit="return validateForm('delete_form', ['gameSearch']);">
        <input type="text" name="gameSearch" placeholder="Cerca gioco per nome">
        <button name="submitAction" type="submit" value="Cerca">Cerca</button>
    </form>
    <ul>
        <!-- Mostra l'elenco dei giochi trovati per la ricerca -->
        <%
            List<Game_bean> games = (List<Game_bean>) request.getAttribute("games");
            if (games != null && !games.isEmpty()) {
                for (Game_bean game : games) {
        %>
                    <li>
                        <p>Nome: <%= game.get_nome() %></p>
                        <form action="${pageContext.request.contextPath}/Gestione_giochi_servlet" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="<%= game.get_id_gioco() %>">
                            <input type="hidden" value="Elimina"> 
                            <button name="submitAction" type="submit" value="Elimina">Elimina</button>
                        </form>
                    </li>
        <%
                }
            }
        %>
    </ul>
</div>

<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>
</html>
