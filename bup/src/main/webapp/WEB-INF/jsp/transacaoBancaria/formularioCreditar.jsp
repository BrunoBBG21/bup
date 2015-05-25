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
		<h3 class="box-title">
			<fmt:message key="transacao.bancaria.formulario.box_header" />
		</h3>
		<div><i class="fa fa-circle-o required"></i><b><fmt:message key="usuario.formulario.obrigatorio" />.</b></div>
	</div>
	<div class="box-body">
		<div class="box-body">
			<form method="post" role="form">
				

				<div class="form-group ${empty errors.from('saldo') ? '' : 'has-error'}">
					<label for="inpValorMinParcela">
						<fmt:message key="usuario.formulario.label.saldo" />:
					</label>
					${usuarioSession.usuarioLogado.saldo}
				</div>


				<div class="form-group ${empty errors.from('conta.id') ? '' : 'has-error'}">
					<label for="inpMidia">
						<c:if
							test="${not empty errors.from('conta.id')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if>  <i class="fa fa-circle-o required" ></i> <fmt:message key="transacao.bancaria.formulario.label.conta" />
					</label>
					<select id="inpMidia" class="form-control" name="transacaoBancaria.conta.id" required>
						<option value="">
							<fmt:message key="combo.selecione" />
						</option>

						<c:forEach var="conta" items="${listaContas}">
							<option value="${conta.id}" <c:if test="${transacaoBancaria.conta.id eq conta.id}">selected</c:if>>
							<fmt:message key="transacao.bancaria.formulario.label.cc" />: ${conta.conta}, 
							<fmt:message key="transacao.bancaria.formulario.label.ag" />: ${conta.agencia}, 
							<fmt:message key="transacao.bancaria.formulario.label.b" />: ${conta.banco}</option>
						</c:forEach>
					</select>
				</div>
				
				<div class="form-group ${empty errors.from('saldo') ? '' : 'has-error'}">
					<label for="inpValorMinParcela">
						<c:if
							test="${not empty errors.from('saldo')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if>  <i class="fa fa-circle-o required" ></i><fmt:message key="transacao.bancaria.formulario.label.saldo" />
					</label>
					<input id="inpValorMinParcela" type="text" class="form-control" name="transacaoBancaria.saldo"
						value="${transacaoBancaria.saldo}" required />
				</div>
				<br />
				
				<c:choose>
			      <c:when test="${transacaoBancaria.id != null}"><input type="hidden" name="transacaoBancaria.id" value="${transacaoBancaria.id}">
					<input type="submit" formaction="<c:url value='/transacaoBancaria/atualizar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.editar"/>' />
			      </c:when>
			
			      <c:otherwise><input type="submit" formaction="<c:url value='/transacaoBancaria/criarCreditar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.salvar"/>' />
			      </c:otherwise>
				</c:choose>
			</form>
		</div>
	</div>
</div>
