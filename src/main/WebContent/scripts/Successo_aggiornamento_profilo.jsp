
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Empress Game</title>
</head>
<body>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
%>

<div>
    <h1>RAggiornamento profilo avvenuto con successo</h1>
     <a href="<%=contextPath %>/">Torna sulla home page</a>
</div>

</body>
</html>
