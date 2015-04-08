<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	${usuarioSession.usuarioLogado.nome} - ${usuarioSession.usuarioLogado.email}
	<br/>
	
	${success}
	<br/>
	<c:forEach var="error" items="${errors}">
	    ${error.category} - ${error.message}<br />
	</c:forEach>
	<br/>
	
	<a href="<c:url value='/login/login'/>">/login/login</a>
	<br/>
	<a href="<c:url value='/login/logout'/>">/login/logout</a>
	<br/>
	<a href="<c:url value='/usuario/formulario'/>">/usuario/formulario</a>
	<br/>
	<a href="<c:url value='/espaco/formulario'/>">/espaco/formulario</a>
	<br/>
	<a href="<c:url value='/midia/formulario'/>">/midia/formulario</a>
	<br/>
	
</body>
</html>