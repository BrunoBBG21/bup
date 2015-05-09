<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="espaco_propaganda.formulario.box_header" />
		</h3>
	</div>

	<div class="box-body table-responsive">
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<c:if test="${usuarioSession.isLogadoAnunciante() }">
						<th>
							<fmt:message key="btn.acoes" />
						</th>
					</c:if>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.url" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.posicaoTela" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.descricao" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.largura" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.altura" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.periodo" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.pageViews" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.midia" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.pertence" />
					</th>
					<th>
						<fmt:message key="espaco_propaganda.listar.header.formatoEspacoPropaganda" />
					</th>

					<c:forEach items="${publicosAlvos}" var="publico" varStatus="i">
						<th>${publico.nome}</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="espaco" items="${espacoPropagandaList}">
					<tr>
						<c:if test="${usuarioSession.isLogadoAnunciante() }">
							<td>
								<form>
									<button type="submit" formaction='<c:url value="/espacoPropaganda/apagar/${espaco.id}"/>'>
										<fmt:message key="btn.apagar" />
									</button>
									<button type="submit" formaction='<c:url value="/espacoPropaganda/editar/${espaco.id}"/>'>
										<fmt:message key="btn.editar" />
									</button>
								</form>
							</td>
						</c:if>

						<td>${espaco.url}</td>
						<td>${espaco.posicaoTela}</td>
						<td>${espaco.descricao}</td>
						<td>${espaco.largura}</td>
						<td>${espaco.altura}</td>
						<td>${espaco.periodo}</td>
						<td>${espaco.pageViews}</td>
						<td>${espaco.midia.tipo}</td>
						<td>${espaco.pertence.nome}</td>
						<td>${espaco.formatoEspacoPropaganda}</td>
						<c:forEach items="${publicosAlvos}" var="publico" varStatus="i">
							<td>
								<input type="checkbox" name="alvos[${i.index}]" value="${publico}" disabled
									<c:forEach items="${espaco.publicosAlvos}" var="alvo"> 
										<c:if test="${publico.id eq alvo.id}">checked</c:if>
        					 		</c:forEach> />
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

