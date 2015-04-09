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
			Inserir Usuário
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
				<h3 class="box-title">Inserir</h3>
			</div>
			<div class="box-body">
				<div class="box-body">
					
					<c:forEach var="error" items="${errors}">
					    ${error.category} - ${error.message}<br />
					</c:forEach>
					
					<form action="<c:url value='/usuario/criar'/>" method="post">
						<fmt:message key="usuario.formulario.label.tipoUsuario"/>:
						<select name="tipoUsuario" >
							<option value="">
								<fmt:message key="combo.selecione"/>
							</option>
							
							<c:forEach var="tipo" items="${tipos}">
								<option 
									value="${tipo.name()}" 
									<c:if test="${tipoUsuario eq tipo.name()}">selected="selected"</c:if>>
									${tipo.descricao}
								</option>
							</c:forEach>
						</select>
						<span class="error">${errors.from('tipoUsuario').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="usuario.formulario.label.email"/>:
						<input type="text" name="email" value="${email}"/>
						<span class="error">${errors.from('email').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="usuario.formulario.label.password"/>:
						<input type="password" name="password" value="${password}"/>
						<span class="error">${errors.from('password').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="usuario.formulario.label.nome"/>:
						<input type="text" name="nome" value="${nome}"/>
						<span class="error">${errors.from('nome').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="usuario.formulario.label.endereco"/>:
						<input type="text" name="endereco" value="${endereco}"/>
						<span class="error">${errors.from('endereco').join(' - ')}</span> 
						<br/> 
						
						<fmt:message key="usuario.formulario.label.cep"/>:
						<input type="text" name="cep" value="${cep}"/>
						<span class="error">${errors.from('cep').join(' - ')}</span> 
						<br/> 
						
						<fmt:message key="usuario.formulario.label.telefone"/>:
						<input type="text" name="telefone" value="${telefone}"/>
						<span class="error">${errors.from('telefone').join(' - ')}</span> 
						<br/>
						
						<fmt:message key="usuario.formulario.label.cpfCnpj"/>:
						<input type="text" name="cpfCnpj" value="${cpfCnpj}"/>
						<span class="error">${errors.from('cpf').join(' - ')}</span>
						<span class="error">${errors.from('cnpj').join(' - ')}</span> 
						<br/>
						
						<input type="submit" value='<fmt:message key="btn.salvar"/>' />
					</form>
					
				
				
				
				
				
				
					<!-- Date dd/mm/yyyy -->
					<div class="form-group">
						<label>Date masks:</label>
						<div class="input-group">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control"
								data-inputmask="'alias': 'dd/mm/yyyy'" data-mask />
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->

					<!-- Date mm/dd/yyyy -->
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</div>
							<input type="text" class="form-control"
								data-inputmask="'alias': 'mm/dd/yyyy'" data-mask />
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->

					<!-- phone mask -->
					<div class="form-group">
						<label>US phone mask:</label>
						<div class="input-group">
							<div class="input-group-addon">
								<i class="fa fa-phone"></i>
							</div>
							<input type="text" class="form-control"
								data-inputmask='"mask": "(999) 999-9999"' data-mask />
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->

					<!-- phone mask -->
					<div class="form-group">
						<label>Intl US phone mask:</label>
						<div class="input-group">
							<div class="input-group-addon">
								<i class="fa fa-phone"></i>
							</div>
							<input type="text" class="form-control"
								data-inputmask="'mask': ['999-999-9999 [x99999]', '+099 99 99 9999[9]-9999']"
								data-mask />
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->

					<!-- IP mask -->
					<div class="form-group">
						<label>IP mask:</label>
						<div class="input-group">
							<div class="input-group-addon">
								<i class="fa fa-laptop"></i>
							</div>
							<input type="text" class="form-control"
								data-inputmask="'alias': 'ip'" data-mask />
						</div>
						<!-- /.input group -->
					</div>
					<!-- /.form group -->

				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</section>
</div>

<c:import url="../includes/footer.jsp"></c:import>