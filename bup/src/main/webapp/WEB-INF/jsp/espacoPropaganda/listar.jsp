<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:import url="../includes/header.jsp"></c:import>

<script type="text/javascript">
      $(function () {
        //Datemask dd/mm/yyyy
        $("#datemask").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
        //Datemask2 mm/dd/yyyy
        $("#datemask2").inputmask("mm/dd/yyyy", {"placeholder": "mm/dd/yyyy"});
        //Money Euro
        $("[data-mask]").inputmask();

        //Date range picker
        $('#reservation').daterangepicker();
        //Date range picker with time picker
        $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'});
        //Date range as a button
        $('#daterange-btn').daterangepicker(
                {
                  ranges: {
                    'Today': [moment(), moment()],
                    'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
                    'Last 7 Days': [moment().subtract('days', 6), moment()],
                    'Last 30 Days': [moment().subtract('days', 29), moment()],
                    'This Month': [moment().startOf('month'), moment().endOf('month')],
                    'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
                  },
                  startDate: moment().subtract('days', 29),
                  endDate: moment()
                },
        function (start, end) {
          $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
        }
        );

        //iCheck for checkbox and radio inputs
        $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
          checkboxClass: 'icheckbox_minimal-blue',
          radioClass: 'iradio_minimal-blue'
        });
        //Red color scheme for iCheck
        $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
          checkboxClass: 'icheckbox_minimal-red',
          radioClass: 'iradio_minimal-red'
        });
        //Flat red color scheme for iCheck
        $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
          checkboxClass: 'icheckbox_flat-green',
          radioClass: 'iradio_flat-green'
        });

        //Colorpicker
        $(".my-colorpicker1").colorpicker();
        //color picker with addon
        $(".my-colorpicker2").colorpicker();

        //Timepicker
        $(".timepicker").timepicker({
          showInputs: false
        });
      });
    </script>


<!-- Left side column. contains the sidebar -->
<c:import url="../includes/menu.jsp"></c:import>
<!-- =============================================== -->

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			<fmt:message key="espaco_propaganda.formulario.header"/>
		</h1>
		<ol class="breadcrumb">
			<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			<li><a href="#">Usuário</a></li>
			<li class="active">Inserir</li>
		</ol>
	</section>
																
	<!-- Main content -->
	<section class="content">

		<!-- Default box -->
		<div class="box">
			<div class="box-header with-border">
				<h3 class="box-title">
					<fmt:message key="espaco_propaganda.formulario.box_header"/>
				</h3>
			</div>
			<div class="box-body">
				<div class="box-body">
					<c:forEach var="error" items="${errors}">
					    ${error.category} - ${error.message}<br />
					</c:forEach>

					<table class="table table-bordered table-striped table-hover">
						<thead>
							<tr>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.url"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.posicaoTela"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.descricao"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.largura"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.altura"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.periodo"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.pageViews"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.midia"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.pertence"/>
								</th>
								<th>
									<fmt:message key="espaco_propaganda.listar.header.formatoEspacoPropaganda"/>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="espaco" items="${espacoPropagandaList}">
							    <tr>
									<td>
										${espaco.url}
									</td>
									<td>
										${espaco.posicaoTela}
									</td>
									<td>
										${espaco.descricao}
									</td>
									<td>
										${espaco.largura}
									</td>
									<td>
										${espaco.altura}
									</td>
									<td>
										${espaco.periodo}
									</td>
									<td>
										${espaco.pageViews}
									</td>
									<td>
										${espaco.midia}
									</td>
									<td>
										${espaco.pertence}
									</td>
									<td>
										${espaco.formatoEspacoPropaganda}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>					
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</section>
</div>

<c:import url="../includes/footer.jsp"></c:import>
