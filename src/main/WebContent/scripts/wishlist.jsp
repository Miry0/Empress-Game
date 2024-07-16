<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Sta_nella_lista_bean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="model.Game_bean" %>
<jsp:useBean id="utente" class="model.Utenti_bean" scope="session"/>

<%
    // Recupera l'oggetto che rappresenta la lista dell'utente
    Collection <Sta_nella_lista_bean> lista_desideri=(Collection <Sta_nella_lista_bean>)request.getAttribute("wishlist"); 
    Collection <Game_bean> games=(Collection <Game_bean>)request.getAttribute("listaGiochi"); //recuperimao anche il catalogo dei giochi
    
   System.out.println("la lista dei giochi che recuperiamo nella lista è" + games);
    
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista Desideri</title>
    <style>
        /* Stile per la tabella dei prodotti */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }
        th {
            display: none; /* Nascondi le intestazioni delle colonne */
        }
        img {
            max-width: 100px;
            max-height: 100px;
        }
        .buttons {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .buttons form {
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <h2>Lista Desideri</h2>

 <%-- Verifica se la lista dei desideri non è vuota --%>
<% if (lista_desideri != null && !lista_desideri.isEmpty()) { %>
    <table>
        <thead>
            <tr>
                <th>Immagine</th>
                <th>Nome Prodotto</th>
                <th> Genere </th>
                <th> Prezzo </th>
                <th>Azioni</th>
            </tr>
        </thead>
        <tbody>
            <%-- Itera sui prodotti nella lista dei desideri --%>
            <% for (Sta_nella_lista_bean product : lista_desideri) { %>
                <%
                    // Cerca il gioco corrispondente nel catalogo
                    Game_bean giocoCorrispondente = null; //creiamoci un game bean dove salvare i vari dati del gioco
                    for (Game_bean gioco : games) {
                        if (gioco.get_id_gioco() == product.get_id_gioco()) { //quando il gioco viene trovato nel catalogo, salviamo i suoi dati in all'interno di "giocoCorrispondente"
                            giocoCorrispondente = gioco;
                            break;
                        }
                    }
                %>
                <% if (giocoCorrispondente != null) { %>
                    <tr>
                        <td>
                            <!-- Da aggiungere la riga per l'immagine -->
                           
                        </td>
                        <td><%= giocoCorrispondente.get_nome() %></td>
                        <td><%= giocoCorrispondente.get_genere() %></td>
                        <td><%= giocoCorrispondente.get_prezzo() %></td>
                        <td class="buttons">
                            <form action="cart" method="post">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="nome_gioco" value="<%= giocoCorrispondente.get_id_gioco() %>">
                                <input type="hidden" name="nome_utente" value="<%= utente.get_nome_utente() %>">
                                <input type="hidden" name="id_lista" value="<%= product.get_id_lista() %>">
                                <button type="submit">Aggiungi al carrello</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/lista_desideri_servlet" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="id_gioco" value="<%= giocoCorrispondente.get_id_gioco() %>">
                                <input type="hidden" name="nome_utente" value="<%= utente.get_nome_utente() %>">
                                <input type="hidden" name="id_lista" value="<%= product.get_id_lista() %>">
                                <button name="lista_desideri" type="submit" value="remove">Rimuovi dalla lista desideri</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            <% } %>
        </tbody>
    </table>
<% } else { %>
    <p>La lista dei desideri è vuota.</p>
<% } %>


</body>
</html>
