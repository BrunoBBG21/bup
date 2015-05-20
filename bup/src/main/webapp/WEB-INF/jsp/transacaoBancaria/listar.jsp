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
			.attr("action", "<c:url value='/transacaoBancaria/apagar/'/>" + recipient);
		})
	});
</script>
<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="publico_alvo.formulario.box_header" />
		</h3>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="modalInscrever" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
						
							<td>
								<form>
									
									<button type="button" class="btn btn-primary" data-id="${transacaoBancaria.id}" data-toggle="modal"
										data-target="#modalApagar">
										<fmt:message key="btn.apagar" />
									</button>
									<c:if test="${usuarioSession.isAdministrador() }">
										<button type="submit" formaction='<c:url value="/transacaoBancaria/editar/${transacaoBancaria.id}"/>'>
											<fmt:message key="btn.editar" />
										</button>
										<button type="submit" formaction='<c:url value="/transacaoBancaria/creditar/${transacaoBancaria.id}"/>'>
											<fmt:message key="btn.creditar" />
										</button>
									</c:if>
								</form>
							</td>
						
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

