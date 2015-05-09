<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
	$(document).ready(
			function() {
				$('#inpData').daterangepicker({
					timePicker : true,
					timePickerIncrement : 30,
					format : 'DD/MM/YYYY h:mm A'
				});
				$('#inpReserva').maskMoney({
					thousands : '.',
					decimal : ','
				});
				$('#inpInscricao').maskMoney({
					thousands : '.',
					decimal : ','
				});

				//monta o campo data corretamente
				if ($("#inpDataInicio").val() != "") {
					$("#inpData").val(
							$("#inpDataInicio").val() + " - "
									+ $("#inpDataFim").val());
				}
			});

	function atualizarCampoData() {
		var valData = $("#inpData").val();
		var valIni;
		var valFim;

		if (valData == "") {
			valIni = "";
			valFim = "";

		} else {
			var valDataSplit = valData.split(" - ");
			valIni = valDataSplit[0];
			valFim = valDataSplit[1];
		}

		$("#inpDataInicio").val(valIni);
		$("#inpDataFim").val(valFim);
	}
</script>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="leilao.formulario.box_header" />
		</h3>
	</div>

	<div class="box-body">
		<form method="post" role="form" style="width: 50%">
			<input id="inpDataInicio" type="hidden" name="leilao.dataInicio" value="${leilao.dataInicio}" />
			<input id="inpDataFim" type="hidden" name="leilao.dataFim" value="${leilao.dataFim}" />

			<div class="form-group ${empty errors.from('dataInicio') && empty errors.from('dataFim') ? '' : 'has-error'}">
				<label for="inpData">
					<fmt:message key="leilao.formulario.data" />
				</label>
				<div class="input-group">
					<div class="input-group-addon">
						<i class="fa fa-clock-o"></i>
					</div>
					<input id="inpData" type="text" class="form-control pull-right" required />
				</div>
			</div>

			<div class="form-group ${empty errors.from('reserva') ? '' : 'has-error'}">
				<label for="inpReserva">
					<fmt:message key="leilao.formulario.reserva" />
				</label>
				<input id="inpReserva" type="text" class="form-control" name="leilao.reserva" value="${leilao.reserva}" />
			</div>

			<div class="form-group ${empty errors.from('inscricao') ? '' : 'has-error'}">
				<label for="inpInscricao">
					<fmt:message key="leilao.formulario.inscricao" />
				</label>
				<input id="inpInscricao" type="text" class="form-control" name="leilao.inscricao" value="${leilao.inscricao}" />
			</div>

			<div class="form-group ${empty errors.from('modalidadePagamento.id') ? '' : 'has-error'}">
				<label for="inpModalidadePagamentoId">
					<fmt:message key="leilao.formulario.modalidadePagamento" />
				</label>
				<select id="inpModalidadePagamentoId" class="form-control" name="leilao.modalidadePagamento.id" required>
					<option value="">
						<fmt:message key="combo.selecione" />
					</option>

					<c:forEach var="modalidadePagamento" items="${modalidadePagamentoList}">
						<option value="${modalidadePagamento.id}"
							<c:if test="${lelao.modalidadePagamento.id eq modalidadePagamento.id}">selected</c:if>>
							${modalidadePagamento.tipo}</option>
					</c:forEach>
				</select>
			</div>

			<input type="hidden" name="leilao.espacoPropaganda.id" value="${leilao.espacoPropaganda.id}" />

			
			<c:choose>
			      <c:when test="${leilao.id != null}"><input type="hidden" name="leilao.id" value="${leilao.id}">
					<input type="submit" formaction="<c:url value='/leilao/atualizar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.editar"/>' onclick="atualizarCampoData();"/>
			      </c:when>
			
			      <c:otherwise><input type="submit" formaction="<c:url value='/leilao/criar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.salvar"/>' onclick="atualizarCampoData();"/>
			      </c:otherwise>
				</c:choose>	
		</form>
	</div>
</div>

