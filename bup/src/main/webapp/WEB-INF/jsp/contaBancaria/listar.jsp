<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="conta_bancaria.formulario.box_header"/>
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
							<fmt:message key="conta_bancaria.formulario.label.agencia"/>
						</th>
						<th>
							<fmt:message key="conta_bancaria.formulario.label.conta"/>
						</th>
						<th>
							<fmt:message key="conta_bancaria.formulario.label.banco"/>
						</th>
						<th>
							<fmt:message key="conta_bancaria.formulario.label.ativa"/>
						</th>
						<th>
							<fmt:message key="conta_bancaria.formulario.label.usuario"/>
						</th>
						<th>
							<fmt:message key="conta_bancaria.formulario.label.apagar"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="contaBancaria" items="${contaBancariaList}">
					    <tr>
							<td>
								${contaBancaria.agencia}
							</td>
							<td>
								${contaBancaria.conta}
							</td>
							<td>
								${contaBancaria.banco}
							</td>
							<td>
								${contaBancaria.ativa}
							</td>
							<td>
								${contaBancaria.usuario.nome}
							</td>
							<td>
								<form>
									<button type="submit" formaction='<c:url value="/contaBancaria/apagar/${contaBancaria.id}"/>' >
										<fmt:message key="conta_bancaria.formulario.label.apagar"/>
									</button>
								</form>
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>					
		</div>
	</div>

