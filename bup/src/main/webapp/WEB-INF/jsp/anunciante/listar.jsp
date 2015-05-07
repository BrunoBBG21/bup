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
						<fmt:message key="usuario.formulario.label.cpf" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="anunciante" items="${anuncianteList}">
					<tr>
						<td>${anunciante.email}</td>
						<td>${anunciante.nome}</td>
						<td>${anunciante.endereco}</td>
						<td>${anunciante.cep}</td>
						<td>${anunciante.telefone}</td>
						<td>${anunciante.cpf}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

