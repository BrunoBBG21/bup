<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="midia.formulario.box_header"/>
		</h3>
	</div>

		<div class="box-body">
			<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
			</c:forEach>
			
			<form action="<c:url value='/midia/criar'/>" method="post">
				<div class="form-group ${empty errors.from('tipo') ? '' : 'has-error'}">
					<label for="inpTipo">
						<fmt:message key="midia.formulario.tipo"/>
					</label>
					<input id="inpTipo" type="text" class="form-control" name="tipo" 
							value="${tipo}" placeholder="Mídia" required />
				</div>
				<input type="submit" class="btn btn-primary"  value='<fmt:message key="btn.salvar"/>' />
			</form>
		</div>
	</div>

