<script type="text/javascript">
			
	
	$(document).on('click', '#btnDialogSalvarPedido', function(e){

		$("#btnacao").val("pedidoregistrar");
		
		var formulario = $('#formitenspedido').serialize();
		
		sendAjaxSalvarPedCliente(formulario);
			
	});
	
	$(document).on('click', '#btnDialogDescartarPedido', function(e){
	
		sendAjaxDescartarPedCliente();
			
	});

	$(document).on('click', '#btnDialogExportarPedidoCsv', function(e){
		
		$("#btnacao").val("downloadpedclientecsv");
		
		var formulario = $('#formitenspedido');
		
		sendAjaxDownloadCsvPedCliente(formulario);
			
	});


	$(document).on('click', '#btnDialogPreencherAtendidos', function(e){
		
		$("#btnacao").val("pedidoatendidos");
		
		var formulario = $('#formitenspedido').serialize();
		
		sendAjaxAtualizarPedCliente(formulario);
			
	});

	$(document).on('click', '#btnDialogEnviaEmail', function(e){
		
		sendMailPedCliente();
					
	});

	$(document).on('click', '#btnDialogImprimir', function(e){
		var left  = ($(window).width()/2)-(900/2),
	    top   = ($(window).height()/2)-(600/2),
	    popup = window.open ("pedidoImprimir.jsp", "Pedido de cliente", "width=900, height=600, top="+top+", left="+left);
		var jqwin = $(popup);
		$(jqwin).blur(function(){
			this.close();
			location.href = "pedClienteRegistrar.jsp";		
		});
		popup.focus();
		location.href = "pedClienteRegistrar.jsp";
		popup.onbeforeunload = function(){
			location.href = "pedClienteRegistrar.jsp";
		};	
	});
	
	$(document).on('click', "#changeQtdValue", function(){
		var valor = $("#QtdatendValue").val();
		$(".atendValues").val(valor);		
	});
	
	</script>

				<div class="center-align div-title-padrao col s12">
						Pedido do Cliente
				</div>

				<div class="col s10 offset-s1">

					<c:set var="readmode" value=""></c:set>
					<c:if test="${!empty pedcliente.cliente.codigoftd}">
						<c:set var="readmode" value="readonly"></c:set>
					</c:if>

					<form id="formitenspedido" action="srl" method="post">
						
						<!-- INÍCIO DA DIV div-title-topo -->
						
						<div class="div-title-topo row">
						
						<div class="noPrint div-title-top left-align col l6 m6 s12">
						
						<span class="item-topico-left">
						    Nº Pedido: ${pedcliente.idpedido} <br>
						    Emissão: <fmt:formatDate
									value="${pedcliente.emissao}" pattern="dd/MM/yyyy" />
						</span>
						
							<br>
							<span class="item-topico-left">
								Cod.Cliente:
								${pedcliente.cliente.codigoftd}
							</span> 
							<input type="text" id="txtdescricaoempresa" ${readmode}
								placeholder="Digite o nome da empresa..."
								name="txtdescricaoempresa" autocomplete="off"
								data-activates="singleDropdownEmpresa" data-beloworigin="true"
								value="${pedcliente.cliente.razaosocial}">
							<p style="font-size: 8pt"
								id="txtdescricaoempresa-description"></p>
							<ul id="singleDropdownEmpresa" constrainWidth="false" class="dropdown-content ac-dropdown col s12"></ul>													

						
						</div>
												
						<!-- INÍCIO DOS BUTTONS -->

								<div class="noPrint right-align col l6 m6 s12 input-group div-title-bottom">

									<a class="btn-floating btn-small waves-effect waves-light" 
									title="Salvar pedido" id="btnDialogSalvarPedido" href="javascript:void(0)">
									<i class="material-icons">save</i></a>

									<a href="javascript:void(0)" title="Preencher atendidos"
										id="btnDialogPreencherAtendidos"
										class="btn-floating btn-small waves-effect waves-light">
										<i class="material-icons">check</i>
									</a>

									<a class="btn-floating btn-small waves-effect waves-light grey"
									id="btnDialogExportarPedidoCsv" 
									href="javascript:void(0)" title="Exportar para .csv">
									<i class="material-icons">file_download</i></a>
									
									<a href="srl?acao=pedclientependente" title="Mostrar somente pendentes"
										class="btn-floating btn-small waves-effect waves-light grey"
										id="btnDialogPedidoPendente"><i class="material-icons">remove_red_eye</i>
									</a>
									<a class="btn-floating btn-small waves-effect waves-light brown" 
									title="Imprimir" href="javascript:void(0)"
									id="btnDialogImprimir">
									<i class="material-icons">local_printshop</i></a>
									
									<a href="javascript:void(0)" title="Sair"
										class="btn-floating btn-small waves-effect waves-light red"
										id="btnDialogDescartarPedido">
										<i class="material-icons">exit_to_app</i>
									</a>

								</div>

								<!-- FIM DOS BUTTONS -->
								<c:set var="gpendsim" value="checked"></c:set>
								<c:set var="gpendnao" value=""></c:set>
	
								<c:if test="${pedcliente.guardarpendencia eq 1}">
									<c:set var="gpendsim" value=""></c:set>
									<c:set var="gpendnao" value="checked"></c:set>							
								</c:if>
								<div class="right-align input-group">
									<div class="noPrint right-align col l3 m3 s12 input-group div-title-bottom">
											<h6>E-mail cliente? </h6>				
											<input type="radio"	class="with-gap" name="emailcliente" id="rd01" value="true">
											<label for="rd01">Sim</label>
											<input type="radio" class="with-gap" name="emailcliente" id="rd02" value="false" checked>
											<label for="rd02">Não</label>
									</div>	
									<div class="noPrint right-align col l3 m3 s12 input-group div-title-bottom">
											<h6>Guardar Pendência? </h6>				
											<input type="radio"	class="with-gap" name="guardapend" id="rd1" value="0" ${gpendsim}>
											<label for="rd1">Sim</label>
											<input type="radio" class="with-gap" name="guardapend" id="rd2" value="1" ${gpendnao}>
											<label for="rd2">Não</label>
									</div>								
								</div>								
						
						</div>
						<!-- FIM DA DIV div-title-topo -->
						
												
						<c:set var="inputreadmode" value="" />
													
						<c:set var="itens" value="${fn:length(pedcliente.itens)}" />
						<div class="row">
														
							<div class="col s12">
								<table class="responsive-table">
									<thead>
										<tr>
											<th>Código</th>
											<th>Descrição</th>
											<th>Preço</th>
											<th>Qt.Ped.</th>
											<th>Total</th>
											<th>Qt.Atend.</th>
											<th>Qt.Pend.</th>
											<th>Estoque</th>
											<th>Previsao</th>
											<c:if test="${!empty usuariologado}">
												<th>......</th>
											</c:if>
										</tr>
								</thead>
								<tbody>
									<c:forEach var="pd" items="${pedcliente.itens}">
										<c:set var="qtde" value="1"></c:set>
										<c:if test="${pd.qtdpedida > 0}">
											<c:set var="qtde" value="${pd.qtdpedida}"></c:set>
										</c:if>

										<c:if test="${pd.qtdpendente gt 0}">
											<c:set var="bgcor" value="d1"></c:set>
										</c:if>
										<c:if test="${pd.qtdpendente eq 0}">
											<c:set var="bgcor" value=""></c:set>
										</c:if>
										
										<tr class="${bgcor}">
											<td>${pd.item.codigo}</td>
											<td>${pd.item.descricao}</td>											
											<td><fmt:formatNumber
													value="${pd.item.preco}" type="number" pattern="#,#00.00#" /></td>
											<td><input type="number" min="1" tabindex="-1"
												name="${pd.item.codigo}" value="${qtde}" id="${pd.item.codigo}pdo"
												${readmode} class="atendValues input-values-pedcliente">
											</td>
											<td><fmt:formatNumber
													value="${pd.qtdpedida * pd.item.preco}" type="number"
													pattern="#,#00.00#" /></td>
											<td><input type="number" min="0" ${inputreadmode} class="input-values-pedcliente"
												name="${pd.item.codigo}atd" value="${pd.qtdatendida}" id="${pd.item.codigo}">
											</td>																																								
											<td>${pd.qtdpendente}</td>
											<td><c:choose>
													<c:when test="${!empty usuariologado}">
															${pd.item.estoque}
														</c:when>
													<c:otherwise>
															${pd.item.nivelestoque}
														</c:otherwise>
												</c:choose></td>

											<td class="noPrint"><c:choose>
													<c:when test="${!empty pd.item.previsao}">
														<span style="color: gray; font-size: 8pt;"> <fmt:formatDate
																value="${pd.item.previsao}" pattern="dd/MM/yyyy" />
														</span>
													</c:when>
													<c:otherwise>
														<span style="color: gray; font-size: 8pt;"> ... </span>
													</c:otherwise>
												</c:choose></td>

											<c:if test="${!empty usuariologado}">
												<td><a href="javascript:void(0)" tabindex="-1"
													onclick="remitempedidoclick('${pd.item.codigo}')">remover</a></td>
											</c:if>
										</tr>
																
									</c:forEach>
								</tbody>	
								<tfoot>
									<tr>
										<td colspan="2" style="text-align: center; font-weight: bold;">${itens}
											itens</td>
										<td style="text-align: right; font-weight: bold;">Totais</td>
										<td style="text-align: right; font-weight: bold;">${pedcliente.qtdtotal}</td>
										<td colspan="2" style="text-align: center; font-weight: bold;"><fmt:formatNumber
												value="${pedcliente.total}" type="number"
												pattern="#,#00.00#" /></td>
										<td colspan="4"></td>
									</tr>
								
									<tr style="background-color: #E8F2ED;">
										
										<td colspan="2" style="text-align: right; font-weight: bold;">
											<div class="col l6 m6 s12 right-align">Desconto(%)</div>											
											<div class="col l4 m4 s12">											
											    <input type="text" size="3" id="txtdesconto"
												        value="<fmt:formatNumber
														value="${pedcliente.desconto * 100}" type="number" pattern="#,#0.#" />"
												placeholder="0"
												style="text-align: right; font-weight: bold; font-family: arial; font-size: 8pt;"
												onkeydown="setDesconto(this, event)"> 
											</div>											
										</td>										
										<td style="text-align: right; font-weight: bold;">Desconto(R$)</td>
										<td>		
											<span
											style="margin-left: 12%; text-align: right; font-weight: bold; font-size: 9pt;"><fmt:formatNumber
													value="${pedcliente.valordesconto}" type="number"
													pattern="#,#00.00#" />
											</span>
										</td>
										
										<td style="text-align: right; font-weight: bold;">Liquido</td>
										<td style="text-align: center; font-weight: bold;"
											id="tdliquido"><fmt:formatNumber
												value="${pedcliente.liquido}" type="number"
												pattern="#,#00.00#" /></td>
										<td colspan="5"></td>
									</tr>
									<tr>
										<td colspan="10"></td>
									</tr>
								</tfoot>
								
								</table>
							</div>
						</div>
						
						<!-- HIDDEN INPUTS -->
						<input type="hidden" id="txtidempresa" name="txtidempresa"
							value="${pedcliente.cliente.codigoftd}">

						<input type="hidden" value="pedidoregistrar" name="acao"
							id="btnacao">
						<input type="hidden" value="1" name="tiporecord">	

					</form>
				</div>

	<!-- TRAVA -->
	<input type="hidden" id="trava-record" value="0">

	<!-- MODALS -->
	<div class="modal noPrint" id="modalItemPedCliAlterar">
		<div class="center-align div-title-modal">
			Alterar a quantidade do item
		</div>		
		<div class="modal-content" id="divmodalitemalterar"></div>
	</div>
