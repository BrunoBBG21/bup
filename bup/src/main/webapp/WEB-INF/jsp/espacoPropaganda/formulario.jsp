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
					
					<form action="<c:url value='/espacoPropaganda/criar'/>" method="post">
						<fmt:message key="espaco_propaganda.formulario.url"/>:
						<input type="text" name="espacoPropaganda.url"/>
						<span class="error">${errors.from('espacoPropaganda.url').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.posicaoTela"/>:
						<input type="text" name="espacoPropaganda.posicaoTela"/>
						<span class="error">${errors.from('espacoPropaganda.posicaoTela').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.descricao"/>:
						<input type="text" name="espacoPropaganda.descricao"/>
						<span class="error">${errors.from('espacoPropaganda.descricao').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.largura"/>:
						<input type="text" name="espacoPropaganda.largura"/>
						<span class="error">${errors.from('espacoPropaganda.largura').join(' - ')}</span> 
						<br/>
				
						<fmt:message key="espaco_propaganda.formulario.altura"/>:
						<input type="text" name="espacoPropaganda.altura"/>
						<span class="error">${errors.from('espacoPropaganda.altura').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.periodo"/>:
						<input type="text" name="espacoPropaganda.periodo"/>
						<span class="error">${errors.from('espacoPropaganda.periodo').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.pageViews"/>:
						<input type="text" name="espacoPropaganda.pageViews"/>
						<span class="error">${errors.from('espacoPropaganda.pageViews').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.pesoMaximo"/>:
						<input type="text" name="espacoPropaganda.pesoMaximo"/>
						<span class="error">${errors.from('espacoPropaganda.pesoMaximo').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.midia"/>: 
						<select name="espacoPropaganda.midia.id">
							<c:forEach var="midia" items="${midias}">
								<option value="${midia.id}">${midia.tipo}</option>
							</c:forEach>
						</select>
						<span class="error">${errors.from('espacoPropaganda.midia').join(' - ')}</span>
						<br/>
						
						<fmt:message key="espaco_propaganda.formulario.formatoEspacoPropaganda"/>: 
						<select name="espacoPropaganda.formatoEspacoPropaganda">
							<c:forEach var="formatoEspaco" items="${formatosEspaco}">
								<option value="${formatoEspaco}">${formatoEspaco}</option>
							</c:forEach>
						</select>
						<span class="error">${errors.from('espacoPropaganda.formatoEspacoPropaganda').join(' - ')}</span>
						<br/>
					
						publicosAlvos: TODO...
						<br/>
						
						<input type="submit" value="Salvar" />
					</form>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</section>
</div>

<c:import url="../includes/footer.jsp"></c:import>
