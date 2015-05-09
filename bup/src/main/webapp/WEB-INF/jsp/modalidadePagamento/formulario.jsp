<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="modalidade_pagamento.formulario.box_header" />
		</h3>
	</div>
	<div class="box-body">
		<div class="box-body">
			<form method="post" role="form">
				<div class="form-group ${empty errors.from('tipo') ? '' : 'has-error'}">
					<label for="inpTipo">
						<fmt:message key="modalidade_pagamento.formulario.tipo" />
					</label>
					<input id="inpTipo" type="text" class="form-control" name="modalidadePagamento.tipo" value="${modalidadePagamento.tipo}" />
				</div>

				<div class="form-group ${empty errors.from('valorMinParcela') ? '' : 'has-error'}">
					<label for="inpValorMinParcela">
						<fmt:message key="modalidade_pagamento.formulario.valorMinParcela" />
					</label>
					<input id="inpValorMinParcela" type="text" class="form-control" name="modalidadePagamento.valorMinParcela"
						value="${modalidadePagamento.valorMinParcela}" />
				</div>

				<div class="form-group ${empty errors.from('maxParcela') ? '' : 'has-error'}">
					<label for="inpMaxParcela">
						<fmt:message key="modalidade_pagamento.formulario.maxParcela" />
					</label>
					<input id="inpMaxParcela" type="text" class="form-control" name="modalidadePagamento.maxParcela"
						value="${modalidadePagamento.maxParcela}" />
				</div>

				<div class="form-group ${empty errors.from('midia.id') ? '' : 'has-error'}">
					<label for="inpMidia">
						<fmt:message key="modalidade_pagamento.formulario.midia" />
					</label>
					<select id="inpMidia" class="form-control" name="modalidadePagamento.midia.id" required>
						<option value="">
							<fmt:message key="combo.selecione" />
						</option>

						<c:forEach var="midia" items="${listaMidias}">
							<option value="${midia.id}" <c:if test="${modalidadePagamento.midia.id eq midia.id}">selected</c:if>>${midia.tipo}</option>
						</c:forEach>
					</select>
					<span class="error">${errors.from('espacoPropaganda.formatoEspacoPropaganda').join(' - ')}</span>
				</div>

				<br />
				
				<c:choose>
			      <c:when test="${modalidadePagamento.id != null}"><input type="hidden" name="modalidadePagamento.id" value="${modalidadePagamento.id}">
					<input type="submit" formaction="<c:url value='/modalidadePagamento/atualizar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.editar"/>' />
			      </c:when>
			
			      <c:otherwise><input type="submit" formaction="<c:url value='/modalidadePagamento/criar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.salvar"/>' />
			      </c:otherwise>
				</c:choose>
			</form>
		</div>
	</div>
</div>
