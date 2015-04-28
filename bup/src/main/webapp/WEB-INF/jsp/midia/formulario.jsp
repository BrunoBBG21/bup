<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			aaaaaaaaaa
		</h3>
	</div>
	<div class="box-body">
		<div class="box-body">
			<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
			</c:forEach>
			
			<form action="<c:url value='/midia/criar'/>" method="post">
				tipo: 
				<input type="text" name="tipo"/>
				<span class="error">${errors.from('tipo').join(' - ')}</span> 
				<br/>
				<input type="submit" value='<fmt:message key="btn.salvar"/>' />
			</form>
		</div>
	</div>
</div>
