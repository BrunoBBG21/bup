<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="espaco_propaganda.formulario.box_header"/>
		</h3>
	</div>
	
		<div class="box-body table-responsive">
			<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
			</c:forEach>
			
			<table class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.url"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.posicaoTela"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.descricao"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.largura"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.altura"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.periodo"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.pageViews"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.midia"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.pertence"/>
						</th>
						<th>
							<fmt:message key="espaco_propaganda.listar.header.formatoEspacoPropaganda"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="espaco" items="${espacoPropagandaList}">
					    <tr>
							<td>
								${espaco.url}
							</td>
							<td>
								${espaco.posicaoTela}
							</td>
							<td>
								${espaco.descricao}
							</td>
							<td>
								${espaco.largura}
							</td>
							<td>
								${espaco.altura}
							</td>
							<td>
								${espaco.periodo}
							</td>
							<td>
								${espaco.pageViews}
							</td>
							<td>
								${espaco.midia.tipo}
							</td>
							<td>
								${espaco.pertence.nome}
							</td>
							<td>
								${espaco.formatoEspacoPropaganda}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>					
		</div>
	</div>

