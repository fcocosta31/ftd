<script type="text/javascript">

$(document).on("click", "#detalhenotafiscal", function(event){
	
	var nota = $(this).attr('alt');
	var items = nota.split(";");
	var numero = items[0];
	var emissao = items[1];
	notafiscalclick(numero, emissao);
	
});								

$(document).on("click", "#btnDialogPrevisao", function(){
	var formulario = $("#formdetalhepedido").serialize();
	sendAjaxAtualizaPrevisao(formulario);
});								

$(document).on("click", "#btnDialogDatas", function(){
	var datetarget = $("#datetarget").pickadate('picker');
	var datereplace = $("#datereplace").pickadate('picker');
	var target = datetarget.get('highlight', 'yyyy-mm-dd');
	var replace = datereplace.get('highlight', 'yyyy-mm-dd');
	$('.dateprev').each(function(index,data) {
		var item = $(this).val();
		if(item == target){
			$(this).val(replace);
		}
	});
	
});

</script>

<div id="divcontent">

	<c:set var="pedido" value="${detalhepedido}" />

	<div class="center-align div-title-padrao col s12">
		Pedido nº ${pedido.idpedido} - Data:								
		<fmt:formatDate value="${pedido.data}" pattern="dd/MM/yyyy" />
	</div>

	<div class="container">

		<div class="row">

			<div class="col s12">

								<c:choose>
									<c:when test="${!empty pedido.notas}">
										<h5>
											<span><strong>Notas Fiscais: </strong></span>
											<c:forEach var="nt" items="${pedido.notas}">
												<span>[<a href="#" id="detalhenotafiscal"
													alt="${nt.idnota};${nt.emissao}">${nt.UF}-${nt.idnota}</a>]
												</span>
											</c:forEach>
										</h5>
									</c:when>
									<c:otherwise>
										<h5 style="color: red">
											<strong>Pedido sem notas emitidas.</strong>
										</h5>
									</c:otherwise>
								</c:choose>

							 <h5>Alterar as datas das previsões</h5>

							<form id="formDetalhePedidoExcel" action="srl" method="post">
							
								<div class="col s12">
										<!-- INÍCIO INPUTS SUBSTITUIÇÃO DAS DATAS -->
										<div class="col l3 m3 s12">
											<label>Data origem:</label>
											<input type="date" class="datepicker validate" id="datetarget">											
										</div>	
										<div class="col l3 m3 s12">
											<label>Data destino:</label>
											<input type="date" class="datepicker validate" id="datereplace">
										</div>
										<div class="col l4 m4 s12">
										    <a href="javascript:void(0)" id="btnDialogDatas"
												title="Substituir datas das previsões"
										 		class="btn-floating btn-small waves-effect waves-light blue">
										 	<i class="material-icons">loop</i></a>	

											<a title="Salvar datas" href="javascript:void(0)"
												class="btn-floating btn-small waves-effect waves-light black loadevent"
												id="btnDialogPrevisao">
												<i class="material-icons">save</i>
											</a>
										</div>
										<!-- FIM INPUTS SUBSTITUIÇÃO DAS DATAS -->
										
										<div class="col l2 m2 s12">
											<a href="javascript:void(0)"
												onclick="javascript:$('#formDetalhePedidoExcel').submit();"><img
												src="resources/images/export_excel.png"
												alt="Gerar planilha excel" title="Exportar para excel"></a>
										</div>
																												
								</div>
								
									<input type="hidden" name="acao" value="detalharPedidoToExcel">
							</form>
							

					</div>

					<br />

					<form id="formdetalhepedido" action="srl" method="post">

						<input type="hidden" name="acao" value="atualizarprevisoes">

						<table class="responsive-table highlight display">
							<thead>
							<tr class="rows">
								<th>Código</th>
								<th>Descrição</th>
								<th class="center-align">Pedido</th>
								<th>Previsão</th>
								<th class="center-align">Atendido</th>
								<th>Dt.Rec.</th>
								<th class="center-align">Pendente</th>
								<th>Observação</th>
							</tr>
							</thead>
							<c:set var="totalpedido" value="0"></c:set>
							<c:set var="totalatendido" value="0"></c:set>
							<c:set var="totalpendente" value="0"></c:set>
							<c:set var="totalitens" value="0"></c:set>
							
							<tbody>
							<c:forEach var="ipd" items="${pedido.itens}">

								<c:set var="totalpedido" value="${totalpedido + ipd.qtdpedida}"></c:set>
								<c:set var="totalitens" value="${totalitens + 1}"></c:set>

								<c:if test="${ipd.cancelado eq 1}">
									<c:set var="bgcor" value="d1"></c:set>
								</c:if>
								<c:if test="${ipd.cancelado eq 0}">
									<c:set var="bgcor" value=""></c:set>
								</c:if>

								<tr class="${bgcor}">
									<td>${ipd.item.codigo}</td>
									<td><span><a href="#!"
											onclick="veritempedidoclick('${ipd.item.codigo}')">${ipd.item.descricao}</a></span></td>
									<td class="right-align">${ipd.qtdpedida}</td>
									<td><input type="date"
										class="dateprev" name="${ipd.item.codigo}prev"										
										value="${ipd.previsao}"></td>
									<td class="right-align"><fmt:formatNumber
											value="${ipd.qtdchegou}" pattern="0" /></td>
									<td><fmt:formatDate value="${ipd.datachegada}" pattern="dd/MM/yyyy"/></td>
									<td class="right-align"><fmt:formatNumber
											value="${ipd.qtdpedida - ipd.qtdchegou}" pattern="0" /></td>
									<td>${ipd.observacao}</td>
								</tr>

								<c:set var="totalatendido"
									value="${totalatendido + ipd.qtdchegou}"></c:set>
								<c:set var="totalpendente"
									value="${totalpendente + (ipd.qtdpedida - ipd.qtdchegou)}"></c:set>
							</c:forEach>
							</tbody>
							<tfoot>
							<tr>
								<td colspan="2" class="center-align">
								Total de itens: ${totalitens}</td>
								<td class="right-align">${totalpedido}</td>
								<td></td>								
								<td class="right-align"><fmt:formatNumber
										value="${totalatendido}" pattern="0" /></td>
								<td></td>		
								<td class="right-align"><fmt:formatNumber
										value="${totalpendente}" pattern="0" /></td>
								<td></td>
							</tr>
							</tfoot>
						</table>

					</form>

			</div>

		</div>

	</div>

<div class="modal" id="modaldetalhenota">
	<div class="center-align div-title-modal" id="modalNotaLabel">
	</div>		
	<div class="modal-content modaldivdetalhenota">
	</div>
	<div class="modal-footer">
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
	</div>		
</div>

<div class="modal" id="modalitempedido">
	<div class="center-align div-title-modal" id="modalItemPedidoLabel">
	</div>		
	<div class="modal-content modaldivitempedido">
	</div>
	<div class="modal-footer">
		<a href="#!" onclick="alteraritempedidoclick()" 
			class="modal-action modal-close waves-effect waves-green btn-flat">Enviar</a>
		<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat"
		onclick="javascript:$('#modaldetalhepedido').modal('open');">Fechar</a>
	</div>		
</div>
