<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<c:forEach var="error" items="${errors}">
	    ${error.category} - ${error.message}<br />
	</c:forEach>
	
	<form action="<c:url value='/espaco/criar'/>" method="post">
	
		url:
		<input type="text" name="espacoPropaganda.url"/>
		<span class="error">${errors.from('espacoPropaganda.url').join(' - ')}</span> 
		<br/>
		
		posicaoTela:
		<input type="text" name="espacoPropaganda.posicaoTela"/>
		<span class="error">${errors.from('espacoPropaganda.posicaoTela').join(' - ')}</span> 
		<br/>
		
		descricao:
		<input type="text" name="espacoPropaganda.descricao"/>
		<span class="error">${errors.from('espacoPropaganda.descricao').join(' - ')}</span> 
		<br/>
		
		largura:
		<input type="text" name="espacoPropaganda.largura"/>
		<span class="error">${errors.from('espacoPropaganda.largura').join(' - ')}</span> 
		<br/>

		altura:
		<input type="text" name="espacoPropaganda.altura"/>
		<span class="error">${errors.from('espacoPropaganda.altura').join(' - ')}</span> 
		<br/>
		
		periodo:
		<input type="text" name="espacoPropaganda.periodo"/>
		<span class="error">${errors.from('espacoPropaganda.periodo').join(' - ')}</span> 
		<br/>
		
		pageViews:
		<input type="text" name="espacoPropaganda.pageViews"/>
		<span class="error">${errors.from('espacoPropaganda.pageViews').join(' - ')}</span> 
		<br/>
		
		pesoMaximo:
		<input type="text" name="espacoPropaganda.pesoMaximo"/>
		<span class="error">${errors.from('espacoPropaganda.pesoMaximo').join(' - ')}</span> 
		<br/>
		
		midia: 
		<select name="espacoPropaganda.midia.id">
			<c:forEach var="midia" items="${midias}">
				<option value="${midia.id}">${midia.tipo}</option>
			</c:forEach>
		</select>
		<span class="error">${errors.from('espacoPropaganda.midia').join(' - ')}</span>
		<br/>
		
		formatoEspacoPropaganda: 
		<select name="espacoPropaganda.formatoEspacoPropaganda">
			<c:forEach var="formatoEspaco" items="${formatosEspaco}">
				<option value="${formatoEspaco}">${formatoEspaco}</option>
			</c:forEach>
		</select>
		<span class="error">${errors.from('espacoPropaganda.formatoEspacoPropaganda').join(' - ')}</span>
		<br/>
	
		publicosAlvos: TODO...
		<br/>
		
		<input type="submit" value="Salvar" />
	</form>
</body>
</html>