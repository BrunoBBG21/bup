<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="usuario.formulario.box_header" />
		</h3>
	</div>
	<div class="box-body table-responsive">
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<c:if test="${usuarioSession.isLogadoAnunciante() }">
						<th>
							<fmt:message key="btn.associar" />
						</th>
					</c:if>
					<th>
						<fmt:message key="usuario.formulario.label.email" />
					</th>

					<th>
						<fmt:message key="usuario.formulario.label.nome" />
					</th>

					<th>
						<fmt:message key="usuario.formulario.label.endereco" />
					</th>

					<th>
						<fmt:message key="usuario.formulario.label.cep" />
					</th>

					<th>
						<fmt:message key="usuario.formulario.label.telefone" />
					</th>
					<th>
						<fmt:message key="usuario.formulario.label.cnpj" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="agencia" items="${agenciaList}">
					<tr>
						<c:if test="${usuarioSession.isLogadoAnunciante() }">
							<td>
								<form>
									<button type="submit" formaction='<c:url value="/agencia/associar/${agencia.id}"/>'>
										<fmt:message key="btn.associar" />
									</button>
								</form>
							</td>
						</c:if>

						<td>${agencia.email}</td>

						<td>${agencia.nome}</td>

						<td>${agencia.endereco}</td>
						<td>${agencia.cep}</td>

						<td>${agencia.telefone}</td>
						<td>${agencia.cnpj}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

