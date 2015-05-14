<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="leilao.leilao.box_header" />
		</h3>
	</div>

	<div class="box-body table-responsive">

		<fmt:message key="leilao.leilao.ultimo.lance">
			<fmt:param value="${ultimoLance.valor}" />
		</fmt:message>

		<form method="post" role="form" style="width: 50%">
			<input type="hidden" name="leilaoId" value="${leilao.id}">

			<div class="form-group ${empty errors.from('valor') ? '' : 'has-error'}">
				<label for="inpValor">
					<fmt:message key="leilao.leilao.valor" />
				</label>
				<input id="inpValor" type="text" class="form-control" name="valor" value="${valor}" />
			</div>

			<button type="submit" formaction="<c:url value='/leilao/lancarLance'/>" class="btn btn-primary">
				<fmt:message key="leilao.leilao.btn.lancar"/>
			</button>
		</form>
	</div>
</div>
