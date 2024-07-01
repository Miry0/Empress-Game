<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Utenti_bean" %>
<%


    // Recupera l'oggetto utente dalla sessione
    Utenti_bean utente = (Utenti_bean) session.getAttribute("utente");
    // Se l'utente non è loggato, reindirizza alla pagina di login
    if (utente == null) {
        response.sendRedirect("Pagina_login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <title>Profilo Utente</title>
    <link rel="stylesheet" type="text/css" href="C:\Users\HP\eclipse-workspace\Empress Game\src\main\WebContent\Style\style.css">
    
</head>
<body>
    <h1>Profilo Utente</h1>
    <form action="AggiornaProfilo_servlet" method="post">
        <label for="nome">Nome:</label>
        <!-- Precompila il campo nome con il valore attuale -->
        <input type="text" id="nome" name="nome" value="<%= utente.get_nome() %>"><br>

        <label for="cognome">Cognome:</label>
        <!-- Precompila il campo cognome con il valore attuale -->
        <input type="text" id="cognome" name="cognome" value="<%= utente.get_cognome() %>"><br>
		
		<label for="email">Email:</label>
        <!-- Precompila il campo cognome con il valore attuale -->
        <input type="text" id="email" name="email" value="<%= utente.get_email() %>"><br>
        
        <!-- Aggiungi altri campi del profilo come necessari -->

        <button type="submit">Aggiorna Profilo</button>
    </form>
    <!-- Link per il logout -->
    <a href="Logout_servlet">Logout</a>
</body>
</html>
