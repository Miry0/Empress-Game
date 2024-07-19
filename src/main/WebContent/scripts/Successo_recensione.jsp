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
    <h1>Recenione registrata con successo!</h1>
     <a href="<%=contextPath %>/">Torna sulla home page</a>
     
    <a href="<%=contextPath %>/scripts/Dettaglio_prodotto.jsp">Torna sul prodotto</a>
</div>

</body>
</html>