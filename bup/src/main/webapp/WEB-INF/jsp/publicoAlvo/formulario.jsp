<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">Inserir</h3>
	</div>
	<div class="box-body">
		<div class="box-body">
			<form action="<c:url value='/publicoAlvo/criar'/>" method="post" role="form">
				<div class="form-group ${empty errors.from('nome') ? '' : 'has-error'}">
					<label for="inpNome">
						<fmt:message key="publico_alvo.formulario.label.nome" />
					</label>
					<input id="inpNome" type="text" class="form-control" name="publicoAlvo.nome" value="${publicoAlvo.nome}" required />
				</div>

				<div class="form-group ${empty errors.from('descricao') ? '' : 'has-error'}">
					<label for="inpDescricao">
						<fmt:message key="publico_alvo.formulario.label.descricao" />
					</label>
					<input id="inpDescricao" type="text" class="form-control" name="publicoAlvo.descricao" value="${publicoAlvo.descricao}" />
				</div>

				<input type="submit" class="btn btn-primary" value='<fmt:message key="btn.salvar"/>' />
			</form>
		</div>
	</div>
</div>
