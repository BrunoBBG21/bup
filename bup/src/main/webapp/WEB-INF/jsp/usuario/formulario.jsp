<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
	
		<fmt:message key="usuario.formulario.label.tipoUsuario"/>:
		<select name="tipoUsuario" >
			<option value="">
				<fmt:message key="combo.selecione"/>
			</option>
			
			<c:forEach var="tipo" items="${tipos}">
				<option 
					value="${tipo.name()}" 
					<c:if test="${tipoUsuario eq tipo.name()}">selected="selected"</c:if>>
					${tipo.descricao}
				</option>
			</c:forEach>
		</select>
		<span class="error">${errors.from('tipoUsuario').join(' - ')}</span> 
		<br/>
		
		<fmt:message key="usuario.formulario.label.email"/>:
		<input type="text" name="email" value="${email}"/>
		<span class="error">${errors.from('email').join(' - ')}</span> 
		<br/>
		
		<fmt:message key="usuario.formulario.label.password"/>:
		<input type="password" name="password" value="${password}"/>
		<span class="error">${errors.from('password').join(' - ')}</span> 
		<br/>
		
		<fmt:message key="usuario.formulario.label.nome"/>:
		<input type="text" name="nome" value="${nome}"/>
		<span class="error">${errors.from('nome').join(' - ')}</span> 
		<br/>
		
		<fmt:message key="usuario.formulario.label.endereco"/>:
		<input type="text" name="endereco" value="${endereco}"/>
		<span class="error">${errors.from('endereco').join(' - ')}</span> 
		<br/> 
		
		<fmt:message key="usuario.formulario.label.cep"/>:
		<input type="text" name="cep" value="${cep}"/>
		<span class="error">${errors.from('cep').join(' - ')}</span> 
		<br/> 
		
		<fmt:message key="usuario.formulario.label.telefone"/>:
		<input type="text" name="telefone" value="${telefone}"/>
		<span class="error">${errors.from('telefone').join(' - ')}</span> 
		<br/>
		
		<fmt:message key="usuario.formulario.label.cpfCnpj"/>:
		<input type="text" name="cpfCnpj" value="${cpfCnpj}"/>
		<span class="error">${errors.from('cpf').join(' - ')}</span>
		<span class="error">${errors.from('cnpj').join(' - ')}</span> 
		<br/>
		
		<input type="submit" value='<fmt:message key="btn.salvar"/>' />
	</form>
</body>
</html>