<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${not empty msgErro}">
		${msgErro}
		<br/>
	</c:if>
	
	<form action="<c:url value='/login/login'/>" method="post">
		Email:
		<input type="text" name="email"/>
		<br/>
		Senha:
		<input type="text" name="senha"/>
		<br/>
		<input type="submit" value="Logar" />
	</form>
</body>
</html>