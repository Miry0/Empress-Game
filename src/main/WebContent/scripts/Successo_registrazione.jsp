
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registrazione avvenuta con successo</title>
</head>
<body>

<!-- Verifica del contesto dell'applicazione -->
<%
    String contextPath = request.getContextPath();
%>

<div>
    <h1>Registrazione avvenuta con successo!</h1>
    <p>Grazie per esserti registrato.</p>
     <a href="<%=contextPath %>/">Torna sulla home page</a>
    <a href="<%=contextPath %>/scripts/Pagina_login.jsp">Accedi alla tua area riservata</a>
</div>
<jsp:include page="footer.jsp" />
</body>
</html>
