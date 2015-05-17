<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="publico_alvo.formulario.box_header" />
		</h3>
	</div>
	<div class="box-body table-responsive">
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<c:if test="${usuarioSession.isAdministrador() }">
						<th>
							<fmt:message key="btn.acoes" />
						</th>
					</c:if>
					<th>
						<fmt:message key="transacao.bancaria.formulario.label.data" />
					</th>
					<th>
						<fmt:message key="usuario.formulario.label.email" />
					</th>

					<th>
						<fmt:message key="usuario.formulario.label.nome" />
					</th>
					<th>
						<fmt:message key="conta_bancaria.formulario.label.agencia" />
					</th>
					<th>
						<fmt:message key="conta_bancaria.formulario.label.conta" />
					</th>
					<th>
						<fmt:message key="conta_bancaria.formulario.label.banco" />
					</th>
					<th>
						<fmt:message key="transacao.bancaria.formulario.label.saldo" />
					</th>
					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="transacaoBancaria" items="${transacaoBancariaList}">
					<tr>
						<c:if test="${usuarioSession.isAdministrador() }">
							<td>
								<form>
									<button type="submit" formaction='<c:url value="/transacaoBancaria/apagar/${transacaoBancaria.id}"/>'>
										<fmt:message key="btn.apagar" />
									</button>
									<button type="submit" formaction='<c:url value="/transacaoBancaria/editar/${transacaoBancaria.id}"/>'>
										<fmt:message key="btn.editar" />
									</button>
								</form>
							</td>
						</c:if>
						<td>${transacaoBancaria.data}</td>
						<td>${transacaoBancaria.usuario.email}</td>
						<td>${transacaoBancaria.usuario.nome}</td>
						<td>${transacaoBancaria.conta.banco}</td>
						<td>${transacaoBancaria.conta.agencia}</td>
						<td>${transacaoBancaria.conta.conta}</td>
						<td>${transacaoBancaria.saldo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

