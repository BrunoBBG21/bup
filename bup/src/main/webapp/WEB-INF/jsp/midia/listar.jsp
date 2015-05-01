<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="midia.listar.box_header"/>
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
							<fmt:message key="midia.formulario.tipo"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="midia" items="${midiaList}">
					    <tr>
							<td>
								${midia.tipo}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>					
		</div>
	</div>

