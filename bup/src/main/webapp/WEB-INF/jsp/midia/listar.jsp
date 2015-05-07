<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="midia.listar.box_header" />
		</h3>
	</div>

	<div class="box-body table-responsive">
		<table class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>
						<fmt:message key="btn.apagar" />
					</th>
					<th>
						<fmt:message key="midia.formulario.tipo" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="midia" items="${midiaList}">
					<tr>
						<td>
							<form>
								<button type="submit" formaction='<c:url value="/midia/apagar/${midia.id}"/>'>
									<fmt:message key="btn.apagar" />
								</button>
							</form>
						</td>
						<td>${midia.tipo}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>

