<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Style/style.css">
<title>Empress Game- Registrazione</title>

</head>

<body>
<%-- <jsp:include page="header.jsp" /> --%>

<h1 id="error_warning">  </h1>
<form  onchange="validate()" action="Registrazione" method="post">
	<fieldset>
		<legend>Dati personali</legend>
			<input type="text" name="nome" placeholder="nome" class="formInput" required autofocus>
			<input type="text" name="cognome" placeholder="cognome" class="formInput" required>
			<input type="text" name="g_nascita" placeholder="giorno nascita 30" class="formInput" required>
			<input type="text" name="m_nascita" placeholder="mese nascita 07" class="formInput" required>
			<input type="text" name="a_nascita" placeholder="annno nascita 1987" class="formInput" required>
			
	</fieldset>
	<fieldset>
	<legend>Dati di login</legend>
	<input type="text" name="nome utente" placeholder="nome utente" class="formInput" required>
	<input type="text" name="email" placeholder="email" class="formInput" required>
	<input type="password" name="pass" placeholder="password" class="formInput" required>
	<input type="password" name="repass" placeholder="reinserisci password" class="formInput" required>
	</fieldset>
	
	<input type="submit" name="invio" value="Invia" class="button1"  onclick="location.href='index.html';">
	<input type="reset" name="reset" value="Reset" class="button1" >
</form>

</body>