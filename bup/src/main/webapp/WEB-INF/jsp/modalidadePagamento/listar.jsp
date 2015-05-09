<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="modalidade_pagamento.listar.box_header" />
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
						<fmt:message key="modalidade_pagamento.listar.tipo" />
					</th>
					<th>
						<fmt:message key="modalidade_pagamento.listar.valorMinParcela" />
					</th>
					<th>
						<fmt:message key="modalidade_pagamento.listar.maxParcela" />
					</th>
					<th>
						<fmt:message key="modalidade_pagamento.listar.midia" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="modalidadePagamento" items="${modalidadePagamentoList}">
					<tr>
						<c:if test="${usuarioSession.isAdministrador() }">
							<td>
								<form>
									<button type="submit" formaction='<c:url value="/modalidadePagamento/apagar/${modalidadePagamento.id}"/>'>
										<fmt:message key="btn.apagar" />
									</button>
									<button type="submit" formaction='<c:url value="/modalidadePagamento/editar/${modalidadePagamento.id}"/>'>
										<fmt:message key="btn.editar" />
									</button>
								</form>
							</td>
						</c:if>
						<td>${modalidadePagamento.tipo}</td>
						<td>${modalidadePagamento.valorMinParcela}</td>
						<td>${modalidadePagamento.maxParcela}</td>
						<td>${modalidadePagamento.midia.tipo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

