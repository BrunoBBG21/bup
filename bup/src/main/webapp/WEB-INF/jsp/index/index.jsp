<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">Title</h3>
		<div class="box-tools pull-right">
			<button class="btn btn-box-tool" data-widget="collapse"
				data-toggle="tooltip" title="Collapse">
				<i class="fa fa-minus"></i>
			</button>
			<button class="btn btn-box-tool" data-widget="remove"
				data-toggle="tooltip" title="Remove">
				<i class="fa fa-times"></i>
			</button>
		</div>
	</div>
	<div class="box-body">
		
		${success}
		<br/>
		
		<c:forEach var="info" items="${vmessages.info}">
		    ${info.category} - ${info.message}<br />
		</c:forEach>
		<br/>
		<c:forEach var="error" items="${errors}">
		    ${error.category} - ${error.message}<br />
		</c:forEach>
		<br/>
		
	</div>
	
	<div class="box-footer">Footer</div>
</div>