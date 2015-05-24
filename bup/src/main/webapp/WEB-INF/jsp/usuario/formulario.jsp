<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
.required {
    color: red;
    padding-right: 5px;
}
.unik {
    color: #F39C12;
    padding-right: 5px;
}
</style>
<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title"><fmt:message key="usuario.formulario.box_title" /></h3>
		<div><i class="fa fa-circle-o required"></i><b><fmt:message key="usuario.formulario.obrigatorio" />.</b></div>
		<div><i class="fa fa-bell-o unik"></i><b><fmt:message key="usuario.formulario.exclusivo" />.</b></div>
	</div>
	<div class="box-body">
		<div class="box-body">
			<form action="<c:url value='/usuario/criar'/>" method="post"
				role="form">
				<div
					class="form-group ${empty errors.from('tipoUsuario') ? '' : 'has-error'}">
					<label for="inpTipoUsuario"> <c:if
							test="${not empty errors.from('tipoUsuario')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if>  <i class="fa fa-circle-o required" ></i> <fmt:message key="usuario.formulario.label.tipoUsuario" />
					</label> <select id="inpTipoUsuario" name="tipoUsuario"
						class="form-control target">
						<option value="">
							<fmt:message key="combo.selecione" />
						</option>

						<c:forEach var="tipo" items="${tipos}">
							<option value="${tipo.name()}"
								<c:if test="${tipoUsuario eq tipo.name()}">selected</c:if>>${tipo.descricao}</option>
						</c:forEach>
					</select>
				</div>
				<div
					class="form-group ${empty errors.from('email') ? '' : 'has-error'}">

					<label for="inpEmail"> <c:if
							test="${not empty errors.from('email')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if> <i class="fa fa-circle-o required" > </i><i class="fa fa-bell-o unik"></i><fmt:message key="usuario.formulario.label.email" />
					</label>

					<div
						class="input-group ${empty errors.from('email') ? '' : 'has-error'}">

						<span
							class="input-group-addon ${empty errors.from('email') ? '' : 'has-error'}"><i
							class="fa fa-envelope"></i></span> <input id="inpEmail" type="email"
							class="form-control" name="email" value="${email}"
							placeholder="Email@email.com" />
					</div>
				</div>

				<!-- -   
				<div class="form-group ${empty errors.from('email') ? '' : 'has-error'}">
					<label for="inpEmail">
						<fmt:message key="usuario.formulario.label.email" />
					</label>
					<input id="inpEmail" type="email" class="form-control" name="email" value="${email}" />
				</div>
 -->
				<div
					class="form-group ${empty errors.from('password') ? '' : 'has-error'}">
					<label for="inpPassword"> <c:if
							test="${not empty errors.from('password')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if> <i class="fa fa-circle-o required" ></i><fmt:message key="usuario.formulario.label.password" />
					</label> <input id="inpPassword" type="password" class="form-control"
						name="password" value="${password}" />
				</div>

				<div
					class="form-group ${empty errors.from('nome') ? '' : 'has-error'}">
					<label for="inpNome"> <c:if
							test="${not empty errors.from('nome')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if><i class="fa fa-circle-o required" ></i><fmt:message
							key="usuario.formulario.label.nome" />
					</label> <input id="inpNome" type="text" class="form-control" name="nome"
						value="${nome}" />
				</div>

				<div
					class="form-group ${empty errors.from('endereco') ? '' : 'has-error'}">
					<label for="inpEndereco"> <c:if
							test="${not empty errors.from('endereco')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if><i class="fa fa-circle-o required" ></i><fmt:message
							key="usuario.formulario.label.endereco" />
					</label> <input id="inpEndereco" type="text" class="form-control"
						name="endereco" value="${endereco}" />
				</div>

				<div
					class="form-group ${empty errors.from('cep') ? '' : 'has-error'}">
					<label for="inpCep">  <c:if
							test="${not empty errors.from('cep')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if><i class="fa fa-circle-o required" ></i><fmt:message
							key="usuario.formulario.label.cep" />
					</label> <input id="inpCep" type="text" class="form-control" name="cep"
						value="${cep}" />
				</div>
				<!-- 
				<div
					class="form-group ${empty errors.from('telefone') ? '' : 'has-error'}">
					<label for="inpTelefone"> <fmt:message
							key="usuario.formulario.label.telefone" />
					</label> <input id="inpTelefone" type="text" name="telefone"
						value="${telefone}" class="form-control"
						placeholder="(00) 0000-0000"
						data-inputmask='"mask": "(99) 9999-9999"' data-mask />
				</div>
	 -->
				<div
					class="form-group ${empty errors.from('telefone') ? '' : 'has-error'}">

					<label for="inpTelefone"> <c:if
							test="${not empty errors.from('telefone')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if><fmt:message
							key="usuario.formulario.label.telefone" />
					</label>

					<div
						class="input-group ${empty errors.from('telefone') ? '' : 'has-error'}">

						<span class="input-group-addon"><i class="fa fa-phone"></i></span>
						<input id="inpTelefone" type="text" class="form-control"
							name="telefone" value="${telefone}" placeholder="(00) 0000-0000"
							data-inputmask='"mask": "(99) 9999-9999"' data-mask />
					</div>
				</div>


				<div
					class="form-group ${empty errors.from('cpf') and empty errors.from('cnpj') ? '' : 'has-error'}">
					<label id="lblCpfCnpj" for="inpCpfCnpj"><c:if
							test="${not empty errors.from('cpf') or not empty errors.from('cnpj')}">
							<i class="fa fa-times-circle-o"></i>
						</c:if><div id="txtCpfCnpj"><i class="fa fa-circle-o required" ></i><i class="fa fa-bell-o unik"></i><fmt:message
							key="usuario.formulario.label.cpfCnpj" /></div>
					</label> <input id="inpCpfCnpj" type="text" class="form-control"
						name="cpfCnpj" value="${cpfCnpj}" />
				</div>
<script>
$( ".target" )
  .change(function () {
    var str = "";
    $( "select option:selected" ).each(function() {
      str = $( this ).val();
    });
    //alert( str );
    if(str == "AGENCIA"){
    	$( "#txtCpfCnpj" ).html( "<i class='fa fa-circle-o required' ></i><i class='fa fa-bell-o unik'></i>"+"<fmt:message key='usuario.formulario.label.cnpj' />" );
    }else{
    	$( "#txtCpfCnpj" ).html( "<i class='fa fa-circle-o required' ></i><i class='fa fa-bell-o unik'></i>"+"<fmt:message key='usuario.formulario.label.cpf' />" );
    }
  })
  .change();
</script>

				<c:choose>
					<c:when test="${id != null}">
						<input type="hidden" name="id" value="${id}">
						<input type="submit"
							formaction="<c:url value='/usuario/atualizar'/>"
							class="btn btn-primary" value='<fmt:message key="btn.editar"/>' />
					</c:when>

					<c:otherwise>
						<input type="submit" formaction="<c:url value='/usuario/criar'/>"
							class="btn btn-primary" value='<fmt:message key="btn.salvar"/>' />
					</c:otherwise>
				</c:choose>
			</form>
		</div>
	</div>
</div>
