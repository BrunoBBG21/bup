<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>
	<c:set var="titleKey">
		<tiles:getAsString name="title" />
	</c:set>
	<fmt:message key="${titleKey}" />
</title>
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>

<link href="/bup/tema/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="http://code.ionicframework.com/ionicons/2.0.0/css/ionicons.min.css" rel="stylesheet" type="text/css" />
<!-- Theme style -->
<link href="/bup/tema/dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
<!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
<link href="/bup/tema/dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />

<link href="/bup/tema/plugins/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
<link href="/bup/tema/plugins/iCheck/all.css" rel="stylesheet" type="text/css" />
<link href="/bup/tema/plugins/colorpicker/bootstrap-colorpicker.min.css" rel="stylesheet" />
<link href="/bup/tema/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet" />
<link href="/bup/tema/plugins/iCheck/all.css" rel="stylesheet" type="text/css" />

<script src="/bup/tema/plugins/jQuery/jQuery-2.1.3.min.js"></script>
<!-- Bootstrap 3.3.2 JS -->
<script src="/bup/tema/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src='/bup/tema/plugins/fastclick/fastclick.min.js'></script>
<!-- AdminLTE App -->
<script src="/bup/tema/dist/js/app.min.js" type="text/javascript"></script>

<!-- InputMask -->
<script src="/bup/tema/plugins/input-mask/jquery.inputmask.js" type="text/javascript"></script>
<script src="/bup/tema/plugins/input-mask/jquery.inputmask.date.extensions.js" type="text/javascript"></script>
<script src="/bup/tema/plugins/input-mask/jquery.inputmask.extensions.js" type="text/javascript"></script>
<script src="/bup/tema/plugins/daterangepicker/daterangepicker.js" type="text/javascript"></script>
<script src="/bup/tema/plugins/colorpicker/bootstrap-colorpicker.min.js" type="text/javascript"></script>
<script src="/bup/tema/plugins/timepicker/bootstrap-timepicker.min.js" type="text/javascript"></script>
<script src="/bup/tema/plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="/bup/tema/plugins/iCheck/icheck.min.js" type="text/javascript"></script>

<!-- Money -->
<script src="/bup/js/jquery.maskMoney.min.js" type="text/javascript"></script>

<!-- AdminLTE for demo purposes -->
<script src="/bup/tema/dist/js/demo.js" type="text/javascript"></script>

<!-- JS do projeto -->
<script src="/bup/js/application.js" type="text/javascript"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
		        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
		        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
		    <![endif]-->
</head>

<body class="skin-yellow">
	<!-- Site wrapper -->
	<div class="wrapper">
		<header class="main-header">
			<tiles:insertAttribute name="header" />
		</header>

		<aside class="main-sidebar">
			<tiles:insertAttribute name="menu" />
		</aside>

		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					<fmt:message key="${titleKey}" />
				</h1>
			</section>

			<!-- Main content -->
			<section class="content">
				<c:forEach var="success" items="${vmessages.success}">
					<div class="alert alert-success alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<h4>
							<i class="icon fa fa-check"></i>
							Sucesso!
						</h4>
						${success.message}
					</div>
				</c:forEach>

				<c:forEach var="info" items="${vmessages.info}">
					<div class="alert alert-info alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<h4>
							<i class="icon fa fa-info"></i>
							Info!
						</h4>
						${info.message}
					</div>
				</c:forEach>

				<c:forEach var="warning" items="${vmessages.warnings}">
					<div class="alert alert-warning alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<h4>
							<i class="icon fa fa-warning"></i>
							Warning!
						</h4>
						${warning.message}
					</div>
				</c:forEach>

				<c:forEach var="error" items="${errors}">
					<div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
						<h4>
							<i class="icon fa fa-ban"></i>
							Erro!
						</h4>
						${error.message}
					</div>
				</c:forEach>

				<tiles:insertAttribute name="body" />
			</section>
		</div>

		<footer class="main-footer">
			<tiles:insertAttribute name="footer" />
		</footer>
	</div>
</body>
</html>
