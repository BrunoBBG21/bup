<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- sidebar: style can be found in sidebar.less -->
<section class="sidebar">
	<form action="<c:url value='/agencia/gerenciar'/>" method="post">
		<c:if test="${usuarioSession.isLogadoAgencia() }">
			<select class="form-control" name="id" onchange="jQuery('#btnSelectGerenciar').click();">
				<option value disabled <c:if test="${!usuarioSession.isGerenciando() }">selected</c:if> style="display: none;">
					<fmt:message key="menu.select.gerenciados.msg.vazia" />
				</option>

				<c:if test="${usuarioSession.isGerenciando() }">
					<option value="-1">
						<fmt:message key="menu.select.gerenciados.msg.desgerenciar" />
					</option>
				</c:if>

				<c:forEach var="anunciante" items="${agenciaController.getGerenciados()}">
					<option <c:if test="${usuarioSession.isGerenciando() && usuarioSession.idGerenciado eq anunciante.id }">selected</c:if>
						value="${anunciante.id}">${anunciante.nome}</option>
				</c:forEach>
			</select>

			<input id="btnSelectGerenciar" hidden type="submit" />
		</c:if>
	</form>

	<!-- sidebar menu: : style can be found in sidebar.less -->
	<ul class="sidebar-menu">
		<li class="header">MAIN NAVIGATION</li>

		<!-- BOTOES VERMELHOS estao desativados -->
		<li class="treeview">
			<a href="#">
				<span>
					<fmt:message key="menu.leilao" />
				</span>
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li data-menu-map="/bup/leilao/listarEspacos,/bup/leilao/formulario">
					<a href="<c:url value='/leilao/listarEspacos'/>">
						<fmt:message key="menu.leilao.criar" />
					</a>
				</li>
				<li data-menu-map="/bup/leilao/listar">
					<a href="<c:url value='/leilao/listar'/>">
						<fmt:message key="menu.leilao.listar" />
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.inscrever" />
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.meus_leiloes" />
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.inscritos" />
					</a>
				</li>
			</ul>
		</li>

		<li class="treeview">
			<a href="#">
				<span>
					<fmt:message key="menu.usuario" />
				</span>
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li data-menu-map="/bup/usuario/formulario">
					<a href="<c:url value='/usuario/formulario'/>">
						<fmt:message key="menu.usuario.cadastro" />
					</a>
				</li>
				<li data-menu-map="/bup/usuario/listar">
					<a href="<c:url value='/usuario/listar'/>">
						<fmt:message key="menu.usuario.listar" />
					</a>
				</li>
				<li data-menu-map="/bup/contaBancaria/listar">
					<a href="<c:url value='/contaBancaria/listar'/>">
						<fmt:message key="menu.conta_bancaria.listar" />
					</a>
				</li>
				<li data-menu-map="/bup/contaBancaria/formulario">
					<a href="<c:url value='/contaBancaria/formulario'/>">
						<fmt:message key="menu.conta_bancaria.cadastro" />
					</a>
				</li>
				<li data-menu-map="/bup/agencia/listar">
					<a href="<c:url value='/agencia/listar'/>">
						<fmt:message key="menu.usuario.listar_agentes" />
					</a>
				</li>
				<li data-menu-map="/bup/anunciante/listar">
					<a href="<c:url value='/anunciante/listar'/>">
						<fmt:message key="menu.usuario.listar_anunciantes" />
					</a>
				</li>
			</ul>
		</li>

		<li class="treeview">
			<a href="#">
				<span>
					<fmt:message key="menu.transferencia" />
				</span>
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<c:if test="${usuarioSession.isAdministrador() }">
					<li>
						<a href="#" style="color: red;">
							<fmt:message key="menu.transferencia.creditar" />
						</a>
					</li>
				</c:if>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.transferencia.retirar" />
					</a>
				</li>
			</ul>
		</li>


		<li class="treeview">
			<a href="#">
				<span>
					<fmt:message key="menu.modalidade_pagamento" />
				</span>
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li data-menu-map="/bup/modalidadePagamento/listar">
					<a href="<c:url value='/modalidadePagamento/listar'/>">
						<fmt:message key="menu.modalidade_pagamento.listar" />
					</a>
				</li>
				<li data-menu-map="/bup/modalidadePagamento/formulario">
					<a href="<c:url value='/modalidadePagamento/formulario'/>">
						<fmt:message key="menu.modalidade_pagamento.cadastrar" />
					</a>
				</li>
			</ul>
		</li>

		<li class="treeview">
			<a href="#">
				<span>
					<fmt:message key="menu.espaco_propaganda" />
				</span>
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li data-menu-map="/bup/espacoPropaganda/listar">
					<a href="<c:url value='/espacoPropaganda/listar'/>">
						<fmt:message key="menu.espaco_propaganda.listar" />
					</a>
				</li>
				<li data-menu-map="/bup/espacoPropaganda/formulario">
					<a href="<c:url value='/espacoPropaganda/formulario'/>">
						<fmt:message key="menu.espaco_propaganda.cadastrar" />
					</a>
				</li>
				<li data-menu-map="/bup/publicoAlvo/listar">
					<a href="<c:url value='/publicoAlvo/listar'/>">
						<fmt:message key="menu.publico_alvo.listar" />
					</a>
				</li>
				<li data-menu-map="/bup/publicoAlvo/formulario">
					<a href="<c:url value='/publicoAlvo/formulario'/>">
						<fmt:message key="menu.publico_alvo.cadastrar" />
					</a>
				</li>
			</ul>
		</li>
		<c:if test="${usuarioSession.isAdministrador() }">
			<li class="treeview">
				<a href="#">
					<span>
						<fmt:message key="menu.midia_propaganda" />
					</span>
					<i class="fa fa-angle-left pull-right"></i>
				</a>
				<ul class="treeview-menu">
					<li data-menu-map="/bup/midia/listar">
						<a href="<c:url value='/midia/listar'/>">
							<fmt:message key="menu.midia_propaganda.listar" />
						</a>
					</li>
					<li data-menu-map="/bup/midia/formulario">
						<a href="<c:url value='/midia/formulario'/>">
							<fmt:message key="menu.midia_propaganda.cadastrar" />
						</a>
					</li>
				</ul>
			</li>
		</c:if>
		<li class="treeview">
			<a href="#">
				<span>
					<fmt:message key="menu.relatorio" />
				</span>
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.relatorio.operacional" />
					</a>
				</li>
			</ul>
		</li>
	</ul>
</section>
