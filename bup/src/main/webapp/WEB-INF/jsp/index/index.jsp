<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="../includes/header.jsp"></c:import>

<!-- Left side column. contains the sidebar -->
<c:import url="../includes/menu.jsp"></c:import>
<!-- =============================================== -->

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Blank page <small>it all starts here</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="#">Examples</a></li>
			<li class="active">Blank page</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">

		<!-- Default box -->
		<div class="box">
			<div class="box-header with-border">
				<h3 class="box-title">Title</h3>
				<div class="box-tools pull-right">
					<button class="btn btn-box-tool" data-widget="collapse"
						data-toggle="tooltip" title="Collapse">
						<i class="fa fa-minus">aaaa</i>
					</button>
					<button class="btn btn-box-tool" data-widget="remove"
						data-toggle="tooltip" title="Remove">
						<i class="fa fa-times">bbbb</i>
					</button>
				</div>
			</div>
			<div class="box-body">
				${usuarioSession.usuarioLogado.nome} - ${usuarioSession.usuarioLogado.email}
				<br/>
				
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
				
				<a href="<c:url value='/login/login'/>">/login/login</a>
				<br/>
				<a href="<c:url value='/login/logout'/>">/login/logout</a>
				<br/>
				<a href="<c:url value='/usuario/formulario'/>">/usuario/formulario</a>
				<br/>
				<a href="<c:url value='/espacoPropaganda/formulario'/>">/espacoPropaganda/formulario</a>
				<br/>
				<a href="<c:url value='/midia/formulario'/>">/midia/formulario</a>
				<br/>
			</div>
			
			<div class="box-footer">Footer</div>
		</div>
	</section>
</div>

<c:import url="../includes/footer.jsp"></c:import>