<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- Default box -->
<div class="box">
	<div class="box-header with-border">
		<h3 class="box-title">
			<fmt:message key="espaco_propaganda.formulario.box_header" />
		</h3>
	</div>
	<div class="box-body">
		<div class="box-body">
			<form method="post" role="form">
				<div class="form-group ${empty errors.from('url') ? '' : 'has-error'}">
					<label for="inpUrl">
						<fmt:message key="espaco_propaganda.formulario.url" />
					</label>
					<input id="inpUrl" type="url" class="form-control" name="espacoPropaganda.url" value="${espacoPropaganda.url}"
						placeholder="http://exemplo" required />
				</div>

				<div class="form-group ${empty errors.from('posicaoTela') ? '' : 'has-error'}">
					<label for="inpPosicaoTela">
						<fmt:message key="espaco_propaganda.formulario.posicaoTela" />
					</label>
					<input id="inpPosicaoTela" type="text" class="form-control" name="espacoPropaganda.posicaoTela"
						value="${espacoPropaganda.posicaoTela}" required />
					<span class="error">${errors.from('espacoPropaganda.posicaoTela').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('descricao') ? '' : 'has-error'}">
					<label for="inpDescricao">
						<fmt:message key="espaco_propaganda.formulario.descricao" />
					</label>
					<input id="inpDescricao" type="text" class="form-control" name="espacoPropaganda.descricao"
						value="${espacoPropaganda.descricao}" />
					<span class="error">${errors.from('espacoPropaganda.descricao').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('largura') ? '' : 'has-error'}">
					<label for="inpLargura">
						<fmt:message key="espaco_propaganda.formulario.largura" />
					</label>
					<input id="inpLargura" type="text" class="form-control" name="espacoPropaganda.largura" value="${espacoPropaganda.largura}" required/>
					<span class="error">${errors.from('espacoPropaganda.largura').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('altura') ? '' : 'has-error'}">
					<label for="inpAltura">
						<fmt:message key="espaco_propaganda.formulario.altura" />
					</label>
					<input id="inpAltura" type="text" class="form-control" name="espacoPropaganda.altura" value="${espacoPropaganda.altura}" required/>
					<span class="error">${errors.from('espacoPropaganda.altura').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('periodo') ? '' : 'has-error'}">
					<label for="inpPeriodo">
						<fmt:message key="espaco_propaganda.formulario.periodo" />
					</label>
					<input id="inpPeriodo" type="text" class="form-control" name="espacoPropaganda.periodo" value="${espacoPropaganda.periodo}" required/>
					<span class="error">${errors.from('espacoPropaganda.periodo').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('pageViews') ? '' : 'has-error'}">
					<label for="inpPageViews">
						<fmt:message key="espaco_propaganda.formulario.pageViews" />
					</label>
					<input id="inpPageViews" type="text" class="form-control" name="espacoPropaganda.pageViews"
						value="${espacoPropaganda.pageViews}" />
					<span class="error">${errors.from('espacoPropaganda.pageViews').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('pesoMaximo') ? '' : 'has-error'}">
					<label for="inpPesoMaximo">
						<fmt:message key="espaco_propaganda.formulario.pesoMaximo" />
					</label>
					<input id="inpPesoMaximo" type="text" class="form-control" name="espacoPropaganda.pesoMaximo"
						value="${espacoPropaganda.pesoMaximo}" />
					<span class="error">${errors.from('espacoPropaganda.pesoMaximo').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('midia.id') ? '' : 'has-error'}">
					<label for="inpMidiaId">
						<fmt:message key="espaco_propaganda.formulario.midia" />
					</label>
					<select id="inpMidiaId" class="form-control" name="espacoPropaganda.midia.id" required>
						<option value="">
							<fmt:message key="combo.selecione" />
						</option>

						<c:forEach var="midia" items="${midias}">
							<option value="${midia.id}" <c:if test="${espacoPropaganda.midia.id eq midia.id}">selected</c:if>>${midia.tipo}</option>
						</c:forEach>
					</select>
					<span class="error">${errors.from('espacoPropaganda.midia').join(' - ')}</span>
				</div>

				<div class="form-group ${empty errors.from('formatoEspacoPropaganda') ? '' : 'has-error'}">
					<label for="inpFormatoEspacoPropaganda">
						<fmt:message key="espaco_propaganda.formulario.formatoEspacoPropaganda" />
					</label>
					<select id="inpFormatoEspacoPropaganda" class="form-control" name="espacoPropaganda.formatoEspacoPropaganda" required>
						<option value="">
							<fmt:message key="combo.selecione" />
						</option>

						<c:forEach var="formatoEspaco" items="${formatosEspaco}">
							<option value="${formatoEspaco}" <c:if test="${espacoPropaganda.formatoEspacoPropaganda eq formatoEspaco}">selected</c:if>>
								${formatoEspaco}</option>
						</c:forEach>
					</select>
					<span class="error">${errors.from('espacoPropaganda.formatoEspacoPropaganda').join(' - ')}</span>
				</div>


				<br />

				<!-- iCheck -->
				<div class="box box-info">
					<div class="box-header">
						<h3 class="box-title">Publicos Alvos:</h3>
					</div>
					<div class="box-body">
						<!-- Minimal style -->

						<c:forEach items="${categoriaAlvos}" var="publicosAlvos" varStatus="i">
							<div class="box box-success">
								<div class="box-header">
									<h3 class="box-title">${publicosAlvos.get(0).descricao}:</h3>
								</div>
								<div class="box-body">
									<div class="checkbox">
										<c:forEach items="${publicosAlvos}" var="publico">
											<label>
												<input type="checkbox" name="espacoPropaganda.publicosAlvos.id" value="${publico.id}"
													<c:forEach items="${espacoPropaganda.publicosAlvos}" var="alvo">
						        					 <c:if test="${publico.id eq alvo.id}">checked</c:if>
						        				</c:forEach> />${publico.nome}
											</label>
										</c:forEach>
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>

				
				<c:choose>
			      <c:when test="${espacoPropaganda.id != null}"><input type="hidden" name="espacoPropaganda.id" value="${espacoPropaganda.id}">
					<input type="submit" formaction="<c:url value='/espacoPropaganda/atualizar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.editar"/>' />
			      </c:when>
			
			      <c:otherwise><input type="submit" formaction="<c:url value='/espacoPropaganda/criar'/>" class="btn btn-primary"
						value='<fmt:message key="btn.salvar"/>' />
			      </c:otherwise>
				</c:choose>
			</form>
		</div>
	</div>
</div>

