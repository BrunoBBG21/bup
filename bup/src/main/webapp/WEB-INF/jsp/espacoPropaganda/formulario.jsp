<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="espaco_propaganda.formulario.box_header"/>
		</h3>
	</div>
	<div class="box-body">
		<div class="box-body">
			
			<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
			</c:forEach>
			
			<form action="<c:url value='/espacoPropaganda/criar'/>" method="post">
				<fmt:message key="espaco_propaganda.formulario.url"/>:
				<input type="text" name="espacoPropaganda.url"/>
				<span class="error">${errors.from('espacoPropaganda.url').join(' - ')}</span> 
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.posicaoTela"/>:
				<input type="text" name="espacoPropaganda.posicaoTela"/>
				<span class="error">${errors.from('espacoPropaganda.posicaoTela').join(' - ')}</span> 
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.descricao"/>:
				<input type="text" name="espacoPropaganda.descricao"/>
				<span class="error">${errors.from('espacoPropaganda.descricao').join(' - ')}</span> 
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.largura"/>:
				<input type="text" name="espacoPropaganda.largura"/>
				<span class="error">${errors.from('espacoPropaganda.largura').join(' - ')}</span> 
				<br/>
		
				<fmt:message key="espaco_propaganda.formulario.altura"/>:
				<input type="text" name="espacoPropaganda.altura"/>
				<span class="error">${errors.from('espacoPropaganda.altura').join(' - ')}</span> 
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.periodo"/>:
				<input type="text" name="espacoPropaganda.periodo"/>
				<span class="error">${errors.from('espacoPropaganda.periodo').join(' - ')}</span> 
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.pageViews"/>:
				<input type="text" name="espacoPropaganda.pageViews"/>
				<span class="error">${errors.from('espacoPropaganda.pageViews').join(' - ')}</span> 
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.pesoMaximo"/>:
				<input type="text" name="espacoPropaganda.pesoMaximo"/>
				<span class="error">${errors.from('espacoPropaganda.pesoMaximo').join(' - ')}</span> 
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.midia"/>: 
				<select name="espacoPropaganda.midia.id">
					<c:forEach var="midia" items="${midias}">
						<option value="${midia.id}">${midia.tipo}</option>
					</c:forEach>
				</select>
				<span class="error">${errors.from('espacoPropaganda.midia').join(' - ')}</span>
				<br/>
				
				<fmt:message key="espaco_propaganda.formulario.formatoEspacoPropaganda"/>: 
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
		</div>
	</div>
</div>
