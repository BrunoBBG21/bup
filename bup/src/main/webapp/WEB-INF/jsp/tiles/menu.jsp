<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- sidebar: style can be found in sidebar.less -->
<section class="sidebar">
	<form action="<c:url value='/agencia/gerenciar'/>" method="post">
		<c:if test="${usuarioSession.isLogadoAgencia() }">
			<select class="form-control" name="id" onchange="jQuery('#btnSelectGerenciar').click();" >
				<option value disabled <c:if test="${!usuarioSession.isGerenciando() }">selected</c:if> style="display:none;">
					<fmt:message key="menu.select.gerenciados.msg.vazia"/>
				</option>
				
				<c:if test="${usuarioSession.isGerenciando() }">
					<option value="-1" >
						<fmt:message key="menu.select.gerenciados.msg.desgerenciar"/>
					</option>
				</c:if>
				
				<c:forEach var="anunciante" items="${agenciaController.getGerenciados()}">
					<option 
							<c:if test="${usuarioSession.isGerenciando() && usuarioSession.idGerenciado eq anunciante.id }">selected</c:if>
							value="${anunciante.id}">
						${anunciante.nome}
					</option>
				</c:forEach>
			</select>
			
			<input id="btnSelectGerenciar" hidden type="submit"  />
		</c:if>
	</form>
	
	<!-- sidebar menu: : style can be found in sidebar.less -->
	<ul class="sidebar-menu">
		<li class="header">MAIN NAVIGATION</li>
		
		<!-- BOTOES VERMELHOS estao desativados -->
		<li class="treeview">
			<a href="#"> 
				<span>
					<fmt:message key="menu.leilao"/>
				</span> 
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.criar"/>
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.listar"/>
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.inscrever"/>
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.meus_leiloes"/>
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.leilao.inscritos"/>
					</a>
				</li>
			</ul>
		</li>
		
		<li class="treeview" id="/bup/usuario">
			<a href="#"> 
				<span>
					<fmt:message key="menu.usuario"/>
				</span> 
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li id="/bup/usuario/formulario">
					<a href="<c:url value='/usuario/formulario'/>">
						<fmt:message key="menu.usuario.cadastro"/>
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.usuario.listar_agentes"/>
					</a>
				</li>
			</ul>
		</li>
		
		<li class="treeview">
			<a href="#"> 
				<span>
					<fmt:message key="menu.transferencia"/>
				</span> 
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.transferencia.creditar"/>
					</a>
				</li>
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.transferencia.retirar"/>
					</a>
				</li>
			</ul>
		</li>
		
		<li class="treeview" id="/bup/modalidadePagamento">
			<a href="#"> 
				<span>
					<fmt:message key="menu.modalidade_pagamento"/>
				</span> 
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.modalidade_pagamento.listar"/>
					</a>
				</li>
				<li id="/bup/modalidadePagamento/formulario">
					<a href="<c:url value='/modalidadePagamento/formulario'/>" >
						<fmt:message key="menu.modalidade_pagamento.cadastrar"/>
					</a>
				</li>
			</ul>
		</li>
		
		<li class="treeview" id="/bup/espacoPropaganda">
			<a href="#"> 
				<span>
					<fmt:message key="menu.espaco_propaganda"/>
				</span> 
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li id="/bup/espacoPropaganda/listar">
					<a href="<c:url value='/espacoPropaganda/listar'/>">
						<fmt:message key="menu.espaco_propaganda.listar"/>
					</a>
				</li>
				<li id="/bup/espacoPropaganda/formulario">
					<a href="<c:url value='/espacoPropaganda/formulario'/>" >
						<fmt:message key="menu.espaco_propaganda.cadastrar"/>
					</a>
				</li>
			</ul>
		</li>
		
		<li class="treeview" id="/bup/midia">
			<a href="#"> 
				<span>
					<fmt:message key="menu.midia_propaganda"/>
				</span> 
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li  id="/bup/midia/listar">
					<a href="<c:url value='/midia/listar'/>" >
						<fmt:message key="menu.midia_propaganda.listar"/>
					</a>
				</li>
				<li id="/bup/midia/formulario">
					<a href="<c:url value='/midia/formulario'/>" >
						<fmt:message key="menu.midia_propaganda.cadastrar"/>
					</a>
				</li>
			</ul>
		</li>
		
		<li class="treeview">
			<a href="#"> 
				<span>
					<fmt:message key="menu.relatorio"/>
				</span> 
				<i class="fa fa-angle-left pull-right"></i>
			</a>
			<ul class="treeview-menu">
				<li>
					<a href="#" style="color: red;">
						<fmt:message key="menu.relatorio.operacional"/>
					</a>
				</li>
			</ul>
		</li>
	</ul>
</section>
