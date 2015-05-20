<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	$(function() {
		$('#modalApagar').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget); // Button that triggered the modal
			var recipient = button.data('id'); // Extract info from data-* attributes
			var modal = $(this);
			modal
			.find('.modal-body form#formModalApagar')
			.attr("action", "<c:url value='/usuario/apagar/'/>" + recipient);
		})
	});
</script>
<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="usuario.formulario.box_header" />
		</h3>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="modalApagar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"><fmt:message key="modal.title.apagar" /></h4>
				</div>
				<div class="modal-body">
					<fmt:message key="msg.confirmacao.apagar" />
					<form id="formModalApagar">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary" form="formModalApagar">
						<fmt:message key="btn.apagar" />
					</button>
				</div>
			</div>
		</div>
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
					<th>
						<fmt:message key="usuario.formulario.label.saldo" />
					</th>

				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty usuario}">
					<tr>
						<td>
							<form>
							<c:if test="${usuarioSession.podeDeletar()}">
								<button type="button" class="btn btn-primary" data-id="${usuario.id}" data-toggle="modal"
										data-target="#modalApagar">
										<fmt:message key="btn.apagar" />
									</button>
								</c:if>
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
						<td>${usuario.saldo}</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>

