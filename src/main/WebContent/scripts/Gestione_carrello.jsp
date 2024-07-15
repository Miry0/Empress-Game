<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.Carrello_bean" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.Collection" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<jsp:useBean id="carrello" class="model.Carrello_bean" scope="session"/>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Empress Game - Carrello</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
</head>
<body>

<%
    String contextPath = request.getContextPath();

    // Controlla se l'utente è già loggato
    if (utente == null || utente.get_nome_utente() == null || utente.get_nome_utente().isEmpty()) {
        response.sendRedirect("Pagina_login.jsp");
        return;
    }

    Collection<Game_bean> games = (Collection<Game_bean>) request.getAttribute("listaGiochi"); //recuperiamo la lista intera dei giochi
    ArrayList<Game_bean> prodotti = new ArrayList<Game_bean>(); //creiamoci un array list dove poter memorizzare l'intera descrizione dei giochi
    ArrayList<Integer> lista = (ArrayList<Integer>) carrello.getGamesList(); //recuperiamo l'id dei giochi nel carrello
    
    if (lista != null) {
        for (int idGioco : lista) {
            for (Game_bean game : games) {
                if (idGioco == game.get_id_gioco()) {
                    Game_bean gioco = new Game_bean();
                    gioco.set_id_gioco(game.get_id_gioco());
                    gioco.set_nome(game.get_nome());
                    gioco.set_prezzo(game.get_prezzo());
                    prodotti.add(gioco);
                }
            }
        }
    }
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

<div class="container">
    <% if (carrello != null) { %>
        <h2>Numero Ordine: <%= carrello.get_n_ordine() %> - Totale: <%= carrello.get_totale() %> €</h2>
        <table>
            <thead>
                <tr>
                    <th>Nome Prodotto</th>
                    <th>Prezzo</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <% for (Game_bean prodotto : prodotti) { %>
                    <tr>
                        <td><%= prodotto.get_nome() %></td>
                        <td><%= prodotto.get_prezzo() %> €</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/Carrello_servlet?id_gioco=<%= prodotto.get_id_gioco() %>"  method="post">
                                <button name="azione_carrello" value="elimina" type="submit">Elimina</button>
                            </form>
                        </td>
                    </tr>
                <% } // Chiude il ciclo for %>
            </tbody>
        </table>
    <% } %>
</div>

<form action="Carrello_servlet" method="post">
    <input type="submit" value="Conferma Ordine">
</form>

</body>
</html>
