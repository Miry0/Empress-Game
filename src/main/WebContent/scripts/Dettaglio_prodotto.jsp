<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Game_bean" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="java.util.Collection" %>
<%@ page import="model.Recensioni_bean" %>


<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Empress Games</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_dettagli_gioco.css">

     <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_barra_ricerca.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style_carrello_profilo.css">

</head>
<body>

<%

	String tipoUtente = (String) session.getAttribute("tipoUtente"); //recupera il tipo del utente
	String contextPath = request.getContextPath();
    Game_bean gioco = (Game_bean) request.getAttribute("gioco");
    String messaggioErrore = (String) request.getAttribute("messaggioErrore");
    String messaggio = (String) request.getAttribute("messaggio");
    
    Collection <Recensioni_bean> recensioni= ( Collection <Recensioni_bean>) request.getAttribute("recensioni");
    System.out.println("recensioni" + recensioni); 
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
    
    <%-- Visualizza il messaggio se presente --%>
    <% if (messaggio != null && !messaggio.isEmpty()) { %>
      <p class="messaggio"><%= messaggio %></p>
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

<h4>Lascia una Recensione</h4>
    <form action="${pageContext.request.contextPath}/recensioni_servlet" method="post" onsubmit="return validateForm();">
        <label for="review">Recensione:</label>
        <textarea id="review" name="review" maxlength="250" placeholder="Massimo 250 caratteri"></textarea>
        <br><br>
         <input type="hidden" name="gioco_aggiungi_recensione" value="<%= gioco.get_id_gioco() %>">
         <input type="hidden" name="nome_utente_recensione" value="<%= utente.get_nome_utente() %>">  
        <button name="recensione" value="add_recensione" type="submit">Invia Recensione</button>
    </form>
    
<h4>Recensioni</h4>
<table border="1">
  <thead>
    <tr>
      <th>Nome Utente</th>
      <th>Recensione</th>
    </tr>
  </thead>
  <tbody>
    <% if (recensioni != null && !recensioni.isEmpty()) { %>
      <% for (Recensioni_bean recensione : recensioni) { %>
       	<% if ( (recensione.get_id_gioco()) == (gioco.get_id_gioco())) { %>
        <tr>
          <td><%= recensione.get_nome_utente() %></td>
          <td><%= recensione.get_testo() %></td>
        </tr>
         <% } %>
      <% } %>
    <% } else { %>
      <tr>
        <td colspan="2">Nessuna recensione disponibile.</td>
      </tr>
    <% } %>
  </tbody>
</table>

  <script type="text/javascript">
        function validateForm() {
            var review = document.getElementById("review").value;
            if (review.length > 250) {
                alert("La recensione non può superare i 250 caratteri.");
                return false;
            }
            return true;
        }
    </script>
    
    
<script src="<%= contextPath %>/scripts/script_index.js"></script>
</body>
</html>
