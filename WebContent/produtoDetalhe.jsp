
	<c:set var="p" value="${detalheproduto}"></c:set>

				<div id="divcontent">
																			
						<div class="row">
																			
							<div class="col l12 m12 s12">
								<div class="col l3 m3 s12 center-align">
									<a href="javascript:void(0)" id="image-detalhe-produto">
										<!-- atribuindo o arquivo de imagem sem-foto a uma variavel chamada url -->
										<c:set	var="url" value="resources/images/pd/sem-foto_800.jpg"></c:set>
		
										<!-- testa se o produto tem foto, caso contrário, atribui a imagem sem-foto -->
										<c:if test="${!empty p.imagem}">
											<c:set var="url" value="${p.imagem}"></c:set>
										</c:if>									
											<img width="100%" height="400px" title="${p.codigo}" src="${url}">
										
									<c:if test="${usuariologado.cargo eq 1 or usuariologado.cargo eq 2}">									
										<button class="btn waves-effect waves-light red" 
											style="width:100%" onclick="editarprodutoclick('${p.codigo}')">
											Editar produto <i class="material-icons" title="Alterar produto">edit</i>
										</button>
									</c:if>															
										
									</a>															
								</div>
								
								<div class="col l9 m9 s12">
									<div id="div-detalhe-produto">
										<span id="span-title-produto">${p.descricao}</span>
											<div id="prod-detail-price">											
													<fmt:formatNumber
														value="${p.preco}" type="number" pattern="#,##0.00#" />
											</div>
										<br/>
										<span><strong>Cod.Referência:
										</strong>${p.codigo}</span>
										<br/>
										<span><strong>Cod.Barras: </strong>${p.codbar}</span><br />
										<span><strong>Autor: </strong>${p.autor}</span> <br />
										<span><strong>Familia: </strong>${p.familia}</span><br/>
										<span><strong>Nivel: </strong>${p.nivel}</span> <br />
										<span><strong>Coleção: </strong>${p.colecao}</span> <br/>
										<span><strong>Disciplina: </strong>${p.disciplina}</span><br/>
										<span><strong>Serie: </strong>${p.serie}</span><br/>
										<span><strong>Páginas: </strong>${p.paginas}</span><br/>
										<span><strong>Ano: </strong>${p.lancto}</span><br/>
										<span><strong>Editora: </strong>${p.editora}</span><br/>

										<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">

										<c:set var="estilo"
											value="color: gray; font-size: 12pt;"></c:set>
										<c:if
											test="${fn:containsIgnoreCase(p.status, 'descontinuado') or fn:containsIgnoreCase(p.status, 'reduzido')}">
											<c:set var="estilo"
												value="color: red; font-weight: bold;font-size: 12pt;"></c:set>
										</c:if>
										
										<span style="${estilo}"><strong>Status: </strong>${p.status}</span> <br />
										<span><strong>Observação: </strong>${p.obs}</span><br />
										<span> <strong>Previsao:</strong>
											<fmt:formatDate value="${p.previsao}"
												pattern="dd/MM/yyyy" />
										</span><br/>
										<span> <strong>Situação:</strong>
											${p.obspedido}
										</span>
										<br/>
										<span>
											<c:if test="${pageUF == 'Maranhão'}">
												<strong>Estoques:</strong>
												Slz: ${p.estoque}, Imp: ${p.fil01}, Stns: ${p.fil02}
											</c:if>
											<c:if test="${pageUF != 'Maranhão'}">
												<strong>Estoque:</strong>
													${p.estoque}
											</c:if>											
										</span>
										</c:if>
										
									</div>
									<c:if test="${!empty usuariologado and usuariologado.cargo != 4}">
										
										<div class="card">
											<form>
												<div>
													<div class="input-field col l4 m4 s12">
														<select id="selpedidos${p.codigo}"
															onchange="loadYearSelection('${p.codigo}',this)">
															<option>Escolha uma opção</option>
															<option value="consultarpedidos">Pedidos Fornecedor</option>
															<option value="consultarnotas">Notas Fornecedor</option>
															<option value="consultaradocoes">Adoções Escolas</option>
															<option value="consultardoacoes">Doações Professores</option>
															<option value="consultarkardex">Vendas no Período</option>
															<option value="consultarpendencias">Pendências de Clientes</option>
														</select>
													</div>
													<div class="input-field col l3 m3 s12">
														<select id="selanos${p.codigo}">
														</select>
													</div>
													<div class="noDisplay input-field col l3 m3 s12" id="div-combo-filiais${p.codigo}">
														<select	name="filial" id="cmbEmpresas${p.codigo}">
														</select>
													</div>													

													<div class="noDisplay input-field col l6 m6 s12" id="div-date-between${p.codigo}">
														<div class="col l6 m6 s12">														    
															<input type="text" placeholder="Data inicial" id="dataini${p.codigo}" class="datepicker validate">
														</div>
														<div class="col l6 m6 s12">				
															<input type="text" placeholder="Data final" id="datafim${p.codigo}" class="datepicker validate">
														</div>
													</div>													
													
													<div class="col l2 m2 s12">
														<button type="button"
															class="waves-effect waves-light btn blue"
															onclick="enviarclick('${p.codigo}','${p.descricao}')">Ok</button>
													</div>
													
												</div>
											</form>
										</div>
									</c:if>
								</div>
							</div>
						</div>


				</div>

				<div class="modal" id="modalpedido">
					<div class="center-align div-title-modal-detalhe">
						<span id="modalCodigo"></span>
						<span class="modal-title" id="modalPedidoLabel"></span>
					</div>		
					<div class="modal-content" id="modaldivpedidos">
					</div>
					<div class="modal-footer">
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
					</div>					
				</div>



				<div class="modal" id="modaldetalhenota">
						<div class="center-align div-title-modal-detalhe">
							<span id="modalNotaLabel"></span>
						</div>		
						<div class="modal-content modaldivdetalhenota">
						</div>
						<div class="modal-footer">
							<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
						</div>					
				</div>

				<div class="modal" id="modaldetalhepedido">
					<div class="center-align div-title-modal-detalhe">
						<span id="modalPedidoLabel"></span>
					</div>						
					<div class="modal-content modaldivpedidos">
					</div>
					<div class="modal-footer">
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
					</div>											
				</div>

				<div class="modal" id="modalitempedido">
					<div class="center-align div-title-modal-detalhe">
						<span id="modalItemPedidoLabel"></span>
					</div>						
					<div class="modal-content modaldivitempedido">
					</div>
					<div class="modal-footer">
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat"
							onclick="alteraritempedidoclick()">Enviar</a>
						<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
					</div>																
				</div>

				<div class="modal noPrint" id="modaldetalhenota">
						<div class="center-align div-title-modal">
							Nota Fiscal
						</div>		
						<div class="modal-content modaldivdetalhenota">
							<div class="modal-footer">
								<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
							</div>
						</div>
				</div>

				<div class="modal transparencia" id="modalimagem">
					<div class="modal-content modaldivimagem" id="modal-content-img">
						<a href="#!" class="modal-action modal-close">
							<img
							src="resources/images/close.png" style="margin: 0% 0% 0% 95%" />
						</a>
					</div>
				</div>											



<script type="text/javascript">
	
	$(window).load(function(){
		$("#selpedidos"+idmodal).trigger('contentChanged');
		loadYearSelection(idmodal,"#selpedidos"+idmodal);
	});

	function openModalImagem(img){
		$("#modalimagem").css("width", "27%");
		$("#modalimagem").css("height", "85%");
		$('#modalimagem').css('background-image', 'url(' + img.src + ')');
		$('#modalimagem').css('background-repeat', 'no-repeat');
		$('#modalimagem').css('background-size', '100%');
		$("#modalimagem").modal('open');		
	};					
	
</script>
				