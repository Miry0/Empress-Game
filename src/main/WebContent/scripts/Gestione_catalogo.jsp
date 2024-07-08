<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="model.Utenti_bean" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="model.Game_bean" %>
<%@ page import="java.util.List" %>


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
  <title>Empress Games - Catalogo Giochi</title>
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
  <a href="#">Logout</a>
  <!-- Aggiungi altre voci del menu qui se necessario -->
</div>

        <!-- Form per Aggiungere o Modificare Gioco -->
 <!-- vengono modificati solo i campi non null, grazie ad un metodo che sta nella servlet -->
 
        <div class="form-group">
            <form id="add_form" action="<%= contextPath %>/Gestione_giochi_servlet" method="post" enctype="multipart/form-data" onsubmit="return validateForm('add_form', ['nome', 'piattaforma', 'genere'], ['g_uscita', 'm_uscita', 'a_uscita' ],['prezzo']);">
                <h3>Aggiungi/Modifica Gioco</h3>
                <input type="hidden" name="id" value="${game.id}">
                <input type="text" name="nome" placeholder="Nome del gioco" value="${game.nome}">
                <input type="text" name="piattaforma" placeholder="Piattaforma" value="${game.piattaforma}">
                <input type="text" name="genere" placeholder="Genere" value="${game.genere}">
                <input type="text" name="prezzo" placeholder="Prezzo" value="${game.prezzo}">
                <input type="text" name="g_uscita" placeholder="Giorno di uscita" value="${game.g_uscita}">
                <input type="text" name="m_uscita" placeholder="Mese di uscita" value="${game.m_uscita}">
                <input type="text" name="a_uscita" placeholder="Anno di uscita" value="${game.a_uscita}">
                <input type="file" name="immagine"> <!-- Campo per caricare l'immagine del gioco -->

                <input type="submit" name="submitAction" value="Aggiungi"> <!-- Pulsante per aggiungere un nuovo gioco -->
                <input type="submit" name="submitAction" value="Modifica"> <!-- Pulsante per modificare un gioco esistente -->
            </form>
        </div>

        <!-- Form per Eliminare Gioco -->
        <div class="game-list">
            <h3>Elimina Gioco</h3>
            <!--  form invia una richiesta alla servlet con un parametro gameSearch per cercare giochi per nome. -->
            <!-- Una volta trovati i giochi corrispondenti, viene mostrato un elenco con ciascun gioco e un pulsante "Elimina" accanto ad ogni voce -->
            <form id="delete_form" action="Gestione_giochi_servlet" method="post" onsubmit="return validateForm('delete_form', ['gameSearch']);">
                <input type="text" name="gameSearch" placeholder="Cerca gioco per nome">
                <input type="submit" value="Cerca">
            </form>
            <ul>
                <!-- Mostra l'elenco dei giochi trovati per la ricerca -->
                <%
                	List<Game_bean> games = (List<Game_bean>) request.getAttribute("gameList");
                    if (games != null && !games.isEmpty()) {
                        for (Game_bean game : games) {
                %>
                            <li>
                                <%= game.get_nome() %>
                                <form action="Gestione_giochi_servlet" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="<%= game.get_id_gioco() %>">
                                    <!-- Quando si preme il pulsante "Elimina" accanto a un gioco nell'elenco, 
                                    il valore di submitAction sarà impostato automaticamente a delete, 
                                    il che indica alla servlet di procedere con l'eliminazione del gioco corrispondente -->
                                    <input type="hidden" name="submitAction" value="delete"> 
                                    <input type="submit" value="Elimina">
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
