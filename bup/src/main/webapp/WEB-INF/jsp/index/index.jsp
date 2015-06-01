<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Modal -->
<div class="modal fade" id="modalLogar" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Logar</h4>
			</div>
			<div class="modal-body">
				<div class="login-box">
					<div class="login-logo">
						<a href="../../index2.html"> <b>BUP</b>
						</a>
					</div>
					<!-- /.login-logo -->

					<div class="login-box-body">
						<p class="login-box-msg">
							<fmt:message key="login.msg.inicial" />
						</p>

						<c:forEach var="error" items="${errors}">
							<div class="alert alert-danger alert-dismissable">
								<button type="button" class="close" data-dismiss="alert"
									aria-hidden="true">&times;</button>
								${error.message}.
							</div>
						</c:forEach>

						<form action='<c:url value="/login/login"></c:url>' method="post">
							<div class="form-group has-feedback">
								<input type="text" class="form-control" placeholder="Email"
									name="email" /> <span
									class="glyphicon glyphicon-envelope form-control-feedback"></span>
							</div>
							<div class="form-group has-feedback">
								<input type="password" class="form-control"
									placeholder="Password" name="senha" /> <span
									class="glyphicon glyphicon-lock form-control-feedback"></span>
							</div>
							<div class="row">
								<div class="col-xs-8">
									<div class="checkbox icheck">
										<label> <input type="checkbox"> Remember Me
										</label>
									</div>
								</div>
								<!-- /.col -->
								<div class="col-xs-4">
									<button type="submit"
										class="btn btn-primary btn-block btn-flat">Sign In</button>
								</div>
								<!-- /.col -->
							</div>
						</form>

						<a href='<c:url value="/usuario/formulario" />'
							class="text-center">Register a new membership</a>

					</div>
					<!-- /.login-box-body -->
				</div>
				<!-- /.login-box -->

				<!-- jQuery 2.1.3 -->
				<script src="/bup/tema/plugins/jQuery/jQuery-2.1.3.min.js"></script>
				<!-- Bootstrap 3.3.2 JS -->
				<script src="/bup/tema/bootstrap/js/bootstrap.min.js"
					type="text/javascript"></script>
				<!-- iCheck -->
				<script src="/bup/tema/plugins/iCheck/icheck.min.js"
					type="text/javascript"></script>
				<script>
					$(function() {
						$('input').iCheck({
							checkboxClass : 'icheckbox_square-blue',
							radioClass : 'iradio_square-blue',
							increaseArea : '20%' // optional
						});
					});
				</script>
			</div>
			
		</div>
	</div>
</div>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">Tela Inicial</h3>
		<div class="box-tools pull-right">
			<button class="btn btn-box-tool" data-widget="collapse"
				data-toggle="tooltip" title="Collapse">
				<i class="fa fa-minus"></i>
			</button>
			<button class="btn btn-box-tool" data-widget="remove"
				data-toggle="tooltip" title="Remove">
				<i class="fa fa-times"></i>
			</button>
		</div>
	</div>
	<div class="box-body">
		Bem vindos ao portal da Bolsa Única de Propaganda. Através desta
		ferramenta é possível efetuar leilões de espaços de propaganda de modo
		simples e rápido. <br /> Cadastre-se gratuitamente e aproveite! <br />
		<br /> <img src="/bup/tema/LogoSiteBUP.jpg">
	</div>

	<div class="box-footer">
		APS 141 <br />GRUPO <br />André Dionisio, <br />Bruno Bastos, <br />Bruno
		Bayma, <br />Roberto Bianco, <br />Amandha Cadorso<br /> <br />Orientadora <br />Tatiana
		Escovedo
	</div>
</div>
