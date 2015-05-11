<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="leilao.listar.box_header" />
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
							UHUL
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

