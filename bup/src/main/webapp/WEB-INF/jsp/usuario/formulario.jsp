<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
     $(function () {
       $("[data-mask]").inputmask();
     });
</script>

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
				<div class="form-group">
					<label for="inpTipoUsuario">
						<fmt:message key="usuario.formulario.label.tipoUsuario"/>
					</label>
					<select id="inpTipoUsuario" name="tipoUsuario" class="form-control" >
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
				</div>
				
				<div class="form-group">
					<label for="inpEmail">
						<fmt:message key="usuario.formulario.label.email"/>
					</label>
					<input id="inpEmail" type="email" class="form-control" name="email" value="${email}"/>
					<span class="error">${errors.from('email').join(' - ')}</span> 
				</div>
				
				<div class="form-group">
					<label for="inpPassword">
						<fmt:message key="usuario.formulario.label.password"/>:
					</label>
					<input id="inpPassword" type="password" class="form-control" name="password" value="${password}"/>
					<span class="error">${errors.from('password').join(' - ')}</span> 
				</div>
				
				<div class="form-group">
					<label for="inpNome">
						<fmt:message key="usuario.formulario.label.nome"/>
					</label>
					<input id="inpNome" type="text" class="form-control" name="nome" value="${nome}"/>
					<span class="error">${errors.from('nome').join(' - ')}</span> 
				</div>
				
				<div class="form-group">
					<label for="inpEndereco">
						<fmt:message key="usuario.formulario.label.endereco"/>
					</label>
					<input id="inpEndereco" type="text" class="form-control" name="endereco" value="${endereco}"/>
					<span class="error">${errors.from('endereco').join(' - ')}</span> 
				</div>
				
				<div class="form-group">
					<label for="inpCep">
						<fmt:message key="usuario.formulario.label.cep"/>
					</label>
					<input id="inpCep" type="text" class="form-control" name="cep" value="${cep}"/>
					<span class="error">${errors.from('cep').join(' - ')}</span> 
				</div>

				<div class="form-group">
					<label for="inpTelefone">
						<fmt:message key="usuario.formulario.label.telefone"/>
					</label>
					<input id="inpTelefone" type="text" name="telefone" value="${telefone}" class="form-control" 
						placeholder="(00) 0000-0000" data-inputmask='"mask": "(99) 9999-9999"' data-mask />
					<span class="error">${errors.from('telefone').join(' - ')}</span>
				</div>
				
				<div class="form-group">
					<label for="inpCpfCnpj">
						<fmt:message key="usuario.formulario.label.cpfCnpj"/>
					</label>
					<input id="inpCpfCnpj" type="text" class="form-control" name="cpfCnpj" value="${cpfCnpj}"/>
					<span class="error">${errors.from('cpf').join(' - ')}</span>
					<span class="error">${errors.from('cnpj').join(' - ')}</span> 
				</div>
				
				<input type="submit" class="btn btn-primary" value='<fmt:message key="btn.salvar"/>' />
			</form>
		</div>
	</div>
</div>
