<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
	$(document).ready(function() {
		setInterval(function() {
			$.getJSON("<c:url value='/leilao/ultimoLance'/>/${leilao.id}", function(result){
			    atualizarValor(result.valor);
			});
		}, 1000); //milisegundos 	
	});
	
	function atualizarValor(valor) {
		jQuery("span#valor").text(valor);
	}

	
/* 
	jQuery.get("/bup/leilao/ultimoLance/2", function(data, status) {
		alert("Data: " + data + "\nStatus: " + status);
		dat = data;
	});
 */
	
</script>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="leilao.leilao.box_header" />
		</h3>
	</div>

	<div class="box-body table-responsive">
		
		<fmt:message key="leilao.leilao.ultimo.lance" />
		<span id="valor"></span>

		<form role="form" style="width: 50%">
			<input type="hidden" name="leilaoId" value="${leilao.id}">

			<div class="form-group ${empty errors.from('valor') ? '' : 'has-error'}">
				<label for="inpValor">
					<fmt:message key="leilao.leilao.valor" />
				</label>
				<input id="inpValor" type="text" class="form-control" name="valor" value="${valor}" />
			</div>

			<button type="submit" formaction="<c:url value='/leilao/lancarLance'/>" class="btn btn-primary">
				<fmt:message key="leilao.leilao.btn.lancar" />
			</button>
		</form>
	</div>
</div>
