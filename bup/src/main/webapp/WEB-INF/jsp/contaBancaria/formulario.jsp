<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="conta_bancaria.formulario.box_header" />
		</h3>
	</div>
	<div class="box-body">
		<div class="box-body">

			<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
			</c:forEach>

			<form action="<c:url value='/contaBancaria/criar'/>" method="post"
				role="form">

				<div
					class="form-group ${empty errors.from('agencia') ? '' : 'has-error'}">
					<label for="inpTipo"> <fmt:message
							key="conta_bancaria.formulario.label.agencia" />
					</label> <input id="inpTipo" type="text" class="form-control"
						name="contaBancaria.agencia" value="${contaBancaria.agencia}" required/>
				</div>

				<div
					class="form-group ${empty errors.from('conta') ? '' : 'has-error'}">
					<label for="inpValorMinParcela"> <fmt:message
							key="conta_bancaria.formulario.label.conta" />
					</label> <input id="inpValorMinParcela" type="text" class="form-control"
						name="contaBancaria.conta" value="${contaBancaria.conta}"  required/>
				</div>


				<div
					class="form-group ${empty errors.from('banco') ? '' : 'has-error'}">
					<label for="inpMaxParcela"> <fmt:message
							key="conta_bancaria.formulario.label.banco" />
					</label> <input id="inpMaxParcela" type="text" class="form-control"
						name="contaBancaria.banco" value="${contaBancaria.banco}"  required/>
				</div>

				<div class="checkbox">                   
					<label> <input type="checkbox" name="contaBancaria.ativa"
						<c:if test="${contaBancaria.ativa}">checked</c:if> />
					<fmt:message key="conta_bancaria.formulario.label.ativa" />
					</label>
				</div>


<!-- 				<div -->
<%-- 					class="form-group ${empty errors.from('usuario.id') ? '' : 'has-error'}"> --%>
<%-- 					<label for="inpMidia"> <fmt:message --%>
<%-- 							key="conta_bancaria.formulario.label.usuario" /> --%>
<!-- 					</label> <select id="inpMidia" class="form-control" -->
<!-- 						name="usuario.id" required> -->
<!-- 						<option value=""> -->
<%-- 							<fmt:message key="combo.selecione" /> --%>
<!-- 						</option> -->

<%-- 						<c:forEach var="usuario" items="${usuarios}"> --%>
<%-- 							<option value="${usuario.id}" --%>
<%-- 								<c:if test="${contaBancaria.usuario.id eq usuario.id}">selected</c:if>> --%>
<%-- 								${usuario.nome}</option> --%>
<%-- 						</c:forEach> --%>
<%-- 					</select> <span class="error">${errors.from('contaBancaria.usuario').join(' - ')}</span> --%>
<!-- 				</div> -->




				<br /> <input type="submit" class="btn btn-primary"
					value='<fmt:message key="btn.salvar"/>' />
			</form>
		</div>
	</div>
</div>
