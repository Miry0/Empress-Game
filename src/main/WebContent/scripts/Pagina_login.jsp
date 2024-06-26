<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="model.Utenti_bean" %>
<jsp:useBean id="registeredUser" class="model.Utenti_bean" scope="session"/>

<%
    // Controlla se l'utente è già loggato
    if (registeredUser != null && registeredUser.getEmail() != null && !registeredUser.getEmail().isEmpty()) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./css/loginpage.css">
    <link rel="icon" href="./img/icon.png">
    <title>Empress Game - Login</title>
</head>
<body>
    <div class="container">
        <div class="forms-container">
            <div class="signin-signup">
                <form action="Login_servlet" method="POST" class="sign-in-form">
                    <h2 class="title">Hai già un account?</h2>
                    <div class="input-field">
                        <i class="fas fa-user"></i>
                        <input type="email" name="username" maxlength="50" placeholder="e-mail" required autofocus/>
                    </div>
                    <div class="input-field">
                        <i class="fas fa-lock"></i>
                        <input type="password" name="password" maxlength="50" placeholder="password" required/>
                    </div>
                    <div>
                        <%-- Mostra il messaggio di errore solo se è stato effettuato un tentativo di login --%>
                        <% if (session.getAttribute("loginAttempted") != null && session.getAttribute("loginError") != null) { %>
                            <h4 style="color: red"><%= session.getAttribute("loginError") %></h4>
                        <% } %>
                    </div>
                    <input type="submit" value="Accedi" class="btn solid"/>
                </form>
            </div>
        </div>
    </div>
    <div class="panels-container">
        <div class="panel left-panel">
            <div class="content">
                <h3 style="margin-bottom: 20px">Crea il tuo account</h3>
                <button class="btn transparent" id="sign-up-btn" onclick="location.href = 'register-form.jsp';">
                    Registrati
                </button>
            </div>
        </div>
    </div>
</body>
</html>
