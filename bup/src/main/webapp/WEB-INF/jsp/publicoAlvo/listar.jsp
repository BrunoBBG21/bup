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
							<fmt:message key="btn.apagar" />
						</th>
					</c:if>
					<th>
						<fmt:message key="publico_alvo.formulario.label.nome" />
					</th>

					<th>
						<fmt:message key="publico_alvo.formulario.label.descricao" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="publicoAlvo" items="${publicoAlvoList}">
					<tr>
						<c:if test="${usuarioSession.isAdministrador() }">
							<td>
								<form>
									<button type="submit" formaction='<c:url value="/publicoAlvo/apagar/${publicoAlvo.id}"/>'>
										<fmt:message key="btn.apagar" />
									</button>
								</form>
							</td>
						</c:if>
						<td>${publicoAlvo.nome}</td>

						<td>${publicoAlvo.descricao}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

