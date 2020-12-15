<script type="text/javascript">	

	window.onload = function(){

		var	nitens = parseInt($("#nitens").val());
		myPagination(nitens);
		
	};	

	$(document).on("keyup", "#txtFilter", function(){ 

		var texto = $(this).val(); 
		
		var count = 1;
		
		$("#ulItens ul > li").css("display", "block");
		
		$("#ulItens ul > li").each(function(){ 
			if($(this).text().toUpperCase().indexOf(texto.toUpperCase()) < 0)
				   $(this).css("display", "none");
			else count++;
		});
		
		
		if($(this).val() == ""){
			var	nitens = parseInt($("#nitens").val());		
			myPagination(nitens);
		}else{
			$('#page-selection').empty();
		}
		
});
	
</script>					

<div class="row">
					<c:set var="at" value="${tabela_servlet}" />

					<c:choose>

						<c:when test="${!empty at}">

							<div class="row noPrint">
							   <div class="col l6 s10">
									<c:set var="totalitens" value="${fn:length(at)}"></c:set>
									<input type="hidden" id="nitens" value="${totalitens}">

									<c:if test="${totalitens eq 0}">
									
										Filtrar: <input type="text" id="txtFilter"
										placeholder="${totalitens} iten(s) pesquisado(s)"
										size="40">
									</c:if>
										<c:if test="${!empty usuariologado}">
										<a href="srl?acao=exportaconsultaexcel"
										onclick="exportaConsultaExcel(e)"><img
										src="resources/images/export_excel.png"
										title="Exportar esta consulta p/ Excel"
										style="float: left; margin-right: 1%"></a></c:if>
									
								</div>
								<div class="col l6 s2"></div>
							</div>

							<div id="ulItens" class="row mytab">
								<div class="col l12 s12">
								<ul style="list-style-type: none">

									<c:set var="countitens" value="0"></c:set>

									<c:forEach var="t" items="${at}">

										<li id="${countitens + 1}" class="div-float">
											
											<div class="row" style="margin: 0% 1% 2% 0%; padding: 3px; position: relative;">
												
												<div class="col l12 m12 s12" style="margin: 0px; padding: 0px; position: relative;">
											
														<c:set var="estilo" value="color: #374278; font-size: 170%;"></c:set>
														
														<!-- alterando o estilo da cor do texto para vermelho -->
														<c:if
															test="${fn:containsIgnoreCase(t.status, 'descontinuado') or fn:containsIgnoreCase(t.status, 'reduzido')}">
															<c:set var="estilo"
																value="color: red; font-size: 170%;"></c:set>
														</c:if>													
														
														<!-- foto do produto -->
														<div style="position: relative;" class="col l6 m6 s6">
															<a href="srl?acao=detalheproduto&txtcodigo=${t.codigo}" tabindex="-1">
																
																<!-- atribuindo o arquivo de imagem sem-foto a uma variavel chamada url -->
																<c:set	var="url" value="resources/images/pd/sem-foto_800.jpg"></c:set>
																
																<!-- testa se o produto tem foto, caso contrário, atribui a imagem sem-foto -->
																<c:if test="${!empty t.imagem}">
																	<c:set var="url" value="${t.imagem}"></c:set>
																</c:if> <img width="90px" height="120px"
																alt="${t.codigo}"
																title="${t.codigo}" src="${url}">
															</a>
														</div>

														<!-- preço do produto -->
														<div style="position: relative;" class="col l6 m6 s6">
																<span style="font-size: 6pt; color: gray; text-align: center">R$ </span><br>															
																<strong style="font-size: 18pt; color: #000099;"><fmt:formatNumber
																		value="${t.preco}" type="number" pattern="#,##0.00#" /></strong>
														</div>

														<div style="position: absolute; top: 110%;">
															<!-- descrição do produto -->
															<div style="${estilo}" class="mytabdescription">
																<strong>${t.descricao}</strong>
															</div>		
															
															<!-- autor do produto -->		
															<span style="font-size: 6pt; color: gray;">${t.autor}</span>
				
															<!-- estoque do produto -->	
															<div style="position: relative;">
															<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">
																<span style="color: #374278; font-size: 8pt;">Estoque: ${t.estoque}</span>
															</c:if>
															<!--  RETIRADO A PEDIDO DE RIBEIRÃO PRETO												
															<c:choose>
																<c:when test="${!empty usuariologado and usuariologado.cargo < 4}">
																	<span style="color: #374278; font-size: 8pt;">Estoque: ${t.estoque}</span>
																</c:when>
																<c:otherwise>
																	<c:set var="stylenvestq"
																		value="color: blue; font-size: 8pt;"></c:set>
																	<c:if
																		test="${(t.nivelestoque eq 'Esgotado') or (t.nivelestoque eq 'Sob Consulta')}">
																		<c:set var="stylenvestq"
																			value="color: red; font-size: 8pt;"></c:set>
																	</c:if>
																	<span style="${stylenvestq}">${t.nivelestoque}</span>
																</c:otherwise>
															</c:choose>
															-->
															</div>
																														
															<!--inputs hidden para possibilitar filtros por obs, colecao, editora -->
															<div style="display: none">
																${t.disciplina}
																${t.serie}
																${t.obs}
																${t.colecao}
																${t.editora}
															</div>
													</div>
												</div>
												
												<!-- botões -->
												<div class="col l12 s12 noPrint"
													style="margin-top: 3px; margin-bottom: 8px;padding: 0px;
													 position: absolute; top:230px;">

													<!-- botões -->	
													<div style="position: relative; top: 20%; padding: 2px; margin-top: 5px;">												

													<a type="button" id="btncart${t.codigo}"
														class="btn-floating btn-small waves-effect waves-light blue"
														onclick="additemclick('${t.codigo}',this)">
														<i class="material-icons" title="Adicionar ao orçamento">shopping_cart</i>
													</a>
													<input type="number" name="${t.codigo}" min="1"
														class="input-sm" id="${t.codigo}Qtde"
														onkeydown="additemclickbox('${t.codigo}', event)"
														value="1"
														style="display: block; float: left; width: 30%; margin-right: 5%; text-align: center">
													</div>

												</div>
												
											</div>
											            
										</li>
																	
										<c:set var="countitens" value="${countitens + 1}"></c:set>

									</c:forEach>

								</ul>
								</div>
							</div>

						</c:when>

						<c:otherwise>
							<div class="col s12 center">
								<h4>Sem resultados para esta consulta!</h4>
							</div>
						</c:otherwise>

					</c:choose>
				
				
				<ul id="page-selection" class="noPrint pagination"></ul>
</div>												