<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="model.Carrello_bean" %>
<%@ page import="model.Utenti_bean" %>

<%
    // Verifica se l'utente ha una sessione attiva
    session = request.getSession(false); //se l'utente non ha la sessione, non viene creata. Perchè noi stiamo facendo solo un controllo
    Utenti_bean utente = null;
    if (session != null) {
        utente = (Utenti_bean) session.getAttribute("utente"); //se esiste una sessione, ci recuperiamo l'utente
    }
    else {
        // Se l'utente non è autenticato, reindirizza alla pagina di login
 		request.getRequestDispatcher("Pagina_login.jsp").forward(request, response);      
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Style/style.css">
</head>
<body>

<header>
    <h1>Empress Game - Carrello</h1>
</header>

<div class="container">
    <% 
        Collection<Carrello_bean> carrello = (Collection<Carrello_bean>) request.getAttribute("carrello");
        if (carrello != null && !carrello.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                	<th>Numero ordine</th>
                    <th>Nome Prodotto</th>
                    <th>Quantità</th>
                    <th>Prezzo</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <% for (Carrello_bean prodotto : carrello) { %>
                    <tr>
                        <td><%= prodotto.get_nome_utente() %></td>
                        <td><%= prodotto.get_totale() %></td>
                        <td>
                            <form action="Carrello_servlet" method="post">
                                <input type="hidden" name="action" value="elimina">
                                <input type="hidden" name="n_ordine" value="<%= prodotto.get_n_ordine() %>">
                                <button value="elimina" type="submit">Elimina</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        <form action="${pageContext.request.contextPath}/ConfermaOrdineServlet" method="post">
            <input type="submit" value="Conferma Ordine">
        </form>
    <% } else { %>
        <p>Il carrello è vuoto.</p>
    <% } %>
</div>

</body>
</html>
