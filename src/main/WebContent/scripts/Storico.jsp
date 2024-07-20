<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Empress Game - Storico degli Ordini</title>
</head>
<body>
    <h1>Storico degli Ordini</h1>
    <table>
        <thead>
            <tr>
                <th>N. Ordine</th>
                <th>Data Ordine</th>
                <th>Totale</th>
                <!-- Aggiungi altre intestazioni se necessario -->
            </tr>
        </thead>
        <tbody>
            <for items="${ordiniCliente}" var="ordine">
                <tr>
                    <td>${ordine.n_ordine}</td>
                    <td>${ordine.data_ordine}</td>
                    <td>${ordine.totale}</td>
                    <!-- Aggiungi altre colonne se necessario -->
                </tr>
            </for>
        </tbody>
    </table>
</body>
</html>
