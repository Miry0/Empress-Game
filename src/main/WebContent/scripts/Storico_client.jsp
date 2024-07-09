<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Storico degli Ordini - Amministratore</title>
</head>
<body>
    <h1>Storico degli Ordini - Amministratore</h1>
    <table>
        <thead>
            <tr>
                <th>N. Ordine</th>
                <th>Data Ordine</th>
                <th>Nome Utente</th>
                <th>Totale</th>
                <!-- Aggiungi altre intestazioni se necessario -->
            </tr>
        </thead>
        <tbody>
            <for items="${ordini}" var="ordine">
                <tr>
                    <td>${ordine.n_ordine}</td>
                    <td>${ordine.data_ordine}</td>
                    <td>${ordine.nome_utente}</td>
                    <td>${ordine.totale}</td>
                    <!-- Aggiungi altre colonne se necessario -->
                </tr>
            </for>
        </tbody>
    </table>
</body>
</html>
