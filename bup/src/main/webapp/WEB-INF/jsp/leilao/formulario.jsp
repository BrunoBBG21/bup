<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
	$(document).ready(function() {
		$('#inpData').daterangepicker({
			timePicker : true,
			timePickerIncrement : 30,
			format : 'MM/DD/YYYY h:mm A'
		});
		$('#inpReserva').maskMoney({
			thousands : '.',
			decimal : ','
		});
		$('#inpInscricao').maskMoney({
			thousands : '.',
			decimal : ','
		});
	});
</script>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="leilao.formulario.box_header" />
		</h3>
	</div>

	<div class="box-body">
		<c:forEach var="error" items="${errors}">
			    ${error.category} - ${error.message}<br />
		</c:forEach>

		<form action="<c:url value='/leilao/criar'/>" method="post" role="form">
			<div class="form-group row ${empty errors.from('leilao.data') ? '' : 'has-error'}">
				<div class="col-xs-6">
					<label for="inpData">
						<fmt:message key="leilao.formulario.data" />
					</label>
					<div class="input-group">
						<div class="input-group-addon">
							<i class="fa fa-clock-o"></i>
						</div>
						<input id="inpData" type="text" class="form-control pull-right" name="leilao.data" value="${leilao.data}"
							data-inputmask="'alias': 'dd/mm/yyyy'" data-mask required />
					</div>
				</div>
			</div>

			<div class="form-group row ${empty errors.from('reserva') ? '' : 'has-error'}">
				<div class="col-xs-6">
					<label for="inpReserva">
						<fmt:message key="leilao.formulario.reserva" />
					</label>
					<div class="input-group">
						<input id="inpReserva" type="text" class="form-control" name="leilao.reserva" value="${leilao.reserva}" />
					</div>
				</div>
			</div>

			<div class="form-group row ${empty errors.from('inscricao') ? '' : 'has-error'}">
				<div class="col-xs-6">
					<label for="inpInscricao">
						<fmt:message key="leilao.formulario.inscricao" />
					</label>
					<div class="input-group">
						<input id="inpInscricao" type="text" class="form-control" name="leilao.inscricao" value="${leilao.inscricao}" />
					</div>
				</div>
			</div>

			<div class="form-group row ${empty errors.from('inscricao') ? '' : 'has-error'}">
				<div class="col-xs-6">
					<label for="inpInscricao">
						<fmt:message key="leilao.formulario.inscricao" />
					</label>
					<div class="input-group">
						<input id="inpInscricao" type="text" class="form-control" name="leilao.inscricao" value="${leilao.inscricao}" />
					</div>
				</div>
			</div>

			<div class="form-group checkbox row">
				<div class="col-xs-6">
					<div class="input-group">
						<input type="checkbox" style="margin-left: 0px;" name="leilao.ativo" <c:if test="${leilao.ativo}">checked</c:if> />
					</div>
					<label>
						<fmt:message key="leilao.formulario.ativo" />
					</label>
				</div>
			</div>

			<div class="form-group row ${empty errors.from('modalidadePagamento.id') ? '' : 'has-error'}">
				<div class="col-xs-6">
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
			</div>

			<input type="submit" class="btn btn-primary" value='<fmt:message key="btn.salvar"/>' />
		</form>
	</div>
</div>

