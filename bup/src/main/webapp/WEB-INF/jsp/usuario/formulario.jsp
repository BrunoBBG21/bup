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
	
	<form action="<c:url value='/usuario/criar'/>" method="post">
		tipoUsuario: 
		<select name="tipoUsuario">
			<option value="AGENCIA">Agencia</option>
			<option value="ANUNCIANTE">Anunciante</option>
		</select>
		<span class="error">${errors.from('tipoUsuario').join(' - ')}</span> 
		<br/>
		email:
		<input type="text" name="email"/>
		<span class="error">${errors.from('email').join(' - ')}</span> 
		<br/>
		password:
		<input type="text" name="password"/>
		<span class="error">${errors.from('password').join(' - ')}</span> 
		<br/>
		nome:
		<input type="text" name="nome"/>
		<span class="error">${errors.from('nome').join(' - ')}</span> 
		<br/>
		endereco:
		<input type="text" name="endereco"/>
		<span class="error">${errors.from('endereco').join(' - ')}</span> 
		<br/> 
		cep:
		<input type="text" name="cep"/>
		<span class="error">${errors.from('cep').join(' - ')}</span> 
		<br/> 
		telefone:
		<input type="text" name="telefone"/>
		<span class="error">${errors.from('telefone').join(' - ')}</span> 
		<br/>
		cpfCnpj:
		<input type="text" name="cpfCnpj"/>
		<span class="error">${errors.from('cpf').join(' - ')}</span>
		<span class="error">${errors.from('cnpj').join(' - ')}</span> 
		<br/>
		
		<input type="submit" value="Salvar" />
	</form>
</body>
</html>