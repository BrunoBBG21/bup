<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="midia.formulario.box_header" />
		</h3>
	</div>

	<div class="box-body">
		<form  method="post" role="form">
			<div class="form-group ${empty errors.from('tipo') ? '' : 'has-error'}">
				<label for="inpTipo">
					<fmt:message key="midia.formulario.tipo" />
				</label>
				<input id="inpTipo" type="text" class="form-control" name="midia.tipo" value="${midia.tipo}" placeholder="Mídia" required />
			</div>
			
			<c:choose>
			      <c:when test="${midia.id != null}"><input type="hidden" name="midia.id" value="${midia.id}">
					<input type="submit" formaction="<c:url value='/midia/atualizar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.editar"/>' />
			      </c:when>
			
			      <c:otherwise><input type="submit" formaction="<c:url value='/midia/criar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.salvar"/>' />
			      </c:otherwise>
				</c:choose>
		</form>
	</div>
</div>

