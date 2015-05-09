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
					<th>
						<fmt:message key="btn.acoes" />
					</th>
					<th>
						<fmt:message key="usuario.formulario.label.id" />
					</th>

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

				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty usuario}">
					<tr>
						<td>
							<form>
								<button type="submit" formaction='<c:url value="/usuario/apagar/${usuario.id}"/>'>
									<fmt:message key="btn.apagar" />
								</button>
								<button type="submit" formaction='<c:url value="/usuario/editar/${usuario.id}"/>'>
										<fmt:message key="btn.editar" />
								</button>
							</form>
						</td>
						<td>${usuario.id}</td>
						<td>${usuario.email}</td>
						<td>${usuario.nome}</td>
						<td>${usuario.endereco}</td>
						<td>${usuario.cep}</td>
						<td>${usuario.telefone}</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>

