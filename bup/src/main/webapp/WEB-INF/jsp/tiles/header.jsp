<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<a href="<c:url value='/'/>" class="logo">
	<b>BUP</b>
</a>

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
					<a  data-toggle="modal"
								data-target="#modalLogar"> Logar </a>
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
