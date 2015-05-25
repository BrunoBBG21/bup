<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
.required {
    color: red;
    padding-right: 5px;
}
.unik {
    color: #F39C12;
    padding-right: 5px;
}
</style>
<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">Inserir</h3>
		<div><i class="fa fa-circle-o required"></i><b><fmt:message key="usuario.formulario.obrigatorio" />.</b></div>
		<div><i class="fa fa-bell-o unik"></i><b><fmt:message key="usuario.formulario.exclusivo" />.</b></div>
	</div>
	<div class="box-body">
		<div class="box-body">
			<form method="post" role="form">
				<div class="form-group ${empty errors.from('nome') ? '' : 'has-error'}">
					<label for="inpNome">
						<c:if
							test="${not empty errors.from('nome')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if> <i class="fa fa-circle-o required" > </i><i class="fa fa-bell-o unik"></i><fmt:message key="publico_alvo.formulario.label.nome" />
					</label>
					<input id="inpNome" type="text" class="form-control" name="publicoAlvo.nome" value="${publicoAlvo.nome}" required/>
				</div>
				
				<div class="form-group ${empty errors.from('descricao') ? '' : 'has-error'}">
					<label for="inpDescricao">
						<c:if
							test="${not empty errors.from('descricao')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if> <i class="fa fa-circle-o required" > </i><i class="fa fa-bell-o unik"></i><fmt:message key="publico_alvo.formulario.label.descricao" />
					</label>
					<input id="inpDescricao" type="text" class="form-control" name="publicoAlvo.descricao" value="${publicoAlvo.descricao}" />
				</div>
				
				<c:choose>
			      <c:when test="${publicoAlvo.id != null}"><input type="hidden" name="publicoAlvo.id" value="${publicoAlvo.id}">
					<input type="submit" formaction="<c:url value='/publicoAlvo/atualizar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.editar"/>' />
			      </c:when>
			
			      <c:otherwise><input type="submit" formaction="<c:url value='/publicoAlvo/criar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.salvar"/>' />
			      </c:otherwise>
				</c:choose>
			</form>
		</div>
	</div>
</div>
