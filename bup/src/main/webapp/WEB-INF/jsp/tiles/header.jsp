<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<a href="<c:url value='/'/>" class="logo">
	<b>BUP</b>
</a>
<c:if test="${!usuarioSession.isLogado() }">
<!-- Modal -->
<div class="modal fade" id="modalLogar" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Logar</h4>
			</div>
			<div class="modal-body">
				<div class="login-box">
					<div class="login-logo">
						<a href="../../index2.html">
							<b>BUP</b>
						</a>
					</div>
					<!-- /.login-logo -->

					<div class="login-box-body">
						<p class="login-box-msg">
							<fmt:message key="login.msg.inicial" />
						</p>

						<c:forEach var="error" items="${errors}">
							<div class="alert alert-danger alert-dismissable">
								<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
								${error.message}.
							</div>
						</c:forEach>

						<form action='<c:url value="/login/login"></c:url>' method="post">
							<div class="form-group has-feedback">
								<input type="text" class="form-control" placeholder="Email" name="email" />
								<span class="glyphicon glyphicon-envelope form-control-feedback"></span>
							</div>
							<div class="form-group has-feedback">
								<input type="password" class="form-control" placeholder="Password" name="senha" />
								<span class="glyphicon glyphicon-lock form-control-feedback"></span>
							</div>
							<div class="row">
								<div class="col-xs-8">
									<div class="checkbox icheck">
										<label>
											<input type="checkbox">
											Remember Me
										</label>
									</div>
								</div>
								<!-- /.col -->
								<div class="col-xs-4">
									<button type="submit" class="btn btn-primary btn-block btn-flat">Sign In</button>
								</div>
								<!-- /.col -->
							</div>
						</form>

						<a href='<c:url value="/usuario/formulario" />' class="text-center">Register a new membership</a>

					</div>
					<!-- /.login-box-body -->
				</div>
				<!-- /.login-box -->

				<script>
					$(function() {
						$('input').iCheck({
							checkboxClass : 'icheckbox_square-blue',
							radioClass : 'iradio_square-blue',
							increaseArea : '20%' // optional
						});
					});
				</script>
			</div>
		</div>
	</div>
</div>
</c:if>
<!-- Header Navbar: style can be found in header.less -->
<nav class="navbar navbar-static-top" role="navigation">
	<!-- Sidebar toggle button-->
	<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
		<span class="sr-only"> Toggle navigation </span>
		<span class="icon-bar"> </span>
		<span class="icon-bar"></span>
		<span class="icon-bar"></span>
	</a>

	<div class="navbar-custom-menu">
		<ul class="nav navbar-nav">

			<!-- QUANDO DESLOGADO... -->
			<c:if test="${!usuarioSession.isLogado() }">
				<li>
					<a href="#" data-toggle="modal" data-target="#modalLogar"> Logar </a>
				</li>
			</c:if>

			<!-- QUANDO LOGADO... -->
			<c:if test="${usuarioSession.isLogado() }">
				<li class="dropdown messages-menu">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-envelope-o"></i>
						<span class="label label-success">${usuarioSession.notificacoesNaoLidas.size() }</span>
					</a>
					<ul class="dropdown-menu">
						<li class="header">
							<fmt:message key="notificacoes.menu.header">
								<fmt:param value="${usuarioSession.notificacoesNaoLidas.size() }" />
							</fmt:message>
						</li>
						<li>
							<ul class="menu">
								<c:forEach items="${usuarioSession.notificacoesNaoLidas}" var="notificacao">
									<li>
										<a href="<c:url value='/notificacao/abrirNotificacao'/>?notificacaoId=${notificacao.id}"> ${notificacao.mensagem } </a>
									</li>
								</c:forEach>
							</ul>
						</li>
						<li class="footer">
							<a href="<c:url value='/notificacao/marcarTodasLidas'/>">
								<fmt:message key="notificacoes.menu.marcar.todas.lidas" />
							</a>
						</li>
					</ul>
				</li>

				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<span class="hidden-xs"> ${usuarioSession.nomeUsuarioLogado} </span>
					</a>

					<ul class="dropdown-menu">
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-left">
								<a href="#" class="btn btn-default btn-flat" disabled> Profile </a>
							</div>
							<div class="pull-right">
								<a href="<c:url value='/login/logout'/>" class="btn btn-default btn-flat"> Deslogar </a>
							</div>
						</li>
					</ul>
				</li>
			</c:if>
		</ul>
	</div>
</nav>
