<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
	$(function() {
		$('#modalInscrever').on('show.bs.modal', function(event) {
			var button = $(event.relatedTarget) // Button that triggered the modal
			var recipient = button.data('leilaoid') // Extract info from data-* attributes
			var modal = $(this)
			modal.find('.modal-body input#leilaoId').val(recipient)
		})
	});
</script>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="leilao.inscrever.box_header" />
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
					<h4 class="modal-title" id="myModalLabel">Modal title</h4>
				</div>
				<div class="modal-body">
					<fmt:message key="leilao.inscrever.msg.confirmacao" />
					<form id="formModalInscrever" action="<c:url value="/leilao/inscricao"/>">
						<input id="leilaoId" type="hidden" name="leilaoId" value="">
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary" form="formModalInscrever">
						<fmt:message key="btn.selecionar" />
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
						<fmt:message key="btn.selecionar" />
					</th>
					<th>
						<fmt:message key="leilao.listar.estado" />
					</th>
					<th>
						<fmt:message key="leilao.listar.descricao" />
					</th>
					<th>
						<fmt:message key="leilao.listar.dataInicio" />
					</th>
					<th>
						<fmt:message key="leilao.listar.dataFim" />
					</th>
					<th>
						<fmt:message key="leilao.formulario.reserva" />
					</th>
					<th>
						<fmt:message key="leilao.formulario.inscricao" />
					</th>
					<th>
						<fmt:message key="leilao.formulario.modalidadePagamento" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="leilao" items="${leilaoList}">
					<tr>
						<td>
							<button type="button" class="btn btn-primary" data-leilaoid="${leilao.id}" data-toggle="modal"
								data-target="#modalInscrever">
								<fmt:message key="btn.selecionar" />
							</button>
						</td>

						<td>${leilao.estado.descricao}</td>
						<td>${leilao.espacoPropaganda.descricao}</td>
						<td>
							<fmt:formatDate value="${leilao.dataInicio}" type="date" pattern="dd/MM/yyyy h:mm a" />
						</td>
						<td>
							<fmt:formatDate value="${leilao.dataFim}" type="date" pattern="dd/MM/yyyy h:mm a" />
						</td>
						<td>${leilao.reserva}</td>
						<td>${leilao.inscricao}</td>
						<td>${leilao.modalidadePagamento.tipo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

