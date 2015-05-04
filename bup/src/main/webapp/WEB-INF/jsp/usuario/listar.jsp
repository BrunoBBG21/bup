<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="usuario.formulario.box_header"/>
		</h3>
	</div>
		<div class="box-body table-responsive">
			<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
			</c:forEach>
			${success}
			<table class="table table-bordered table-striped table-hover">
				<thead>
					<tr>
						<th>
							<fmt:message key="usuario.formulario.label.id"/>
						</th>
					
						<th>
							<fmt:message key="usuario.formulario.label.email"/>
						</th>

						<th>
							<fmt:message key="usuario.formulario.label.nome"/>
						</th>

						<th>
							<fmt:message key="usuario.formulario.label.endereco"/>
						</th>

						<th>
							<fmt:message key="usuario.formulario.label.cep"/>
						</th>
						
						<th>
							<fmt:message key="usuario.formulario.label.telefone"/>
						</th>
						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usuario" items="${usuarioList}">
					    <tr>
					    	<td>
								${usuario.id}
							</td>
							
					    	<td>
								${usuario.email}
							</td>
							
					    	<td>
								${usuario.nome}
							</td>
							
					    	<td>
								${usuario.endereco}
							</td>
							<td>
								${usuario.cep}
							</td>
						
					    	<td>
								${usuario.telefone}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>					
		</div>
	</div>

