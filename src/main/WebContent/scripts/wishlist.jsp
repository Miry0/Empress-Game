<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="model.Sta_nella_lista_bean" %>
<%@ page import="java.util.Collection" %>

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
    <% if (request.getAttribute("wishlist") != null && !((Collection<Sta_nella_lista_bean>) request.getAttribute("wishlist")).isEmpty()) { %>
        <table>
            <thead>
                <tr>
                    <th>Immagine</th>
                    <th>Nome Prodotto</th>
                    <th>Azioni</th>
                </tr>
            </thead>
            <tbody>
                <%-- Itera sui prodotti nella lista dei desideri --%>
                <% for (Sta_nella_lista_bean product : (Collection<Sta_nella_lista_bean>) request.getAttribute("wishlist")) { %>
                    <tr>
                        <td>
                         <!--   da aggiustare <img src="data:image/jpeg;base64,<%= new String(org.apache.commons.codec.binary.Base64.encodeBase64(product.get_immagine())) %>" alt="<%= product.get_nome_gioco() %>"> --> 
                        </td>
                        <td><%= product.get_nome_gioco() %></td>
                        <td class="buttons">
                            <form action="cart" method="post">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="nome_gioco" value="<%= product.get_nome_gioco() %>">
                                <input type="hidden" name="nome_utente" value="<%= product.get_nome_utente() %>">
                                <input type="hidden" name="id_lista" value="<%= product.get_id_lista() %>">
                                <button type="submit">Aggiungi al carrello</button>
                            </form>
                            <form action="wishlist" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="nome_gioco" value="<%= product.get_nome_gioco() %>">
                                <input type="hidden" name="nome_utente" value="<%= product.get_nome_utente() %>">
                                <input type="hidden" name="id_lista" value="<%= product.get_id_lista() %>">
                                <button type="submit">Rimuovi dalla lista desideri</button>
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <p>La lista dei desideri è vuota.</p>
    <% } %>

</body>
</html>
