<script type="text/javascript">	

	$(document).on("click", "#detalhenotafiscal", function(event){		    
		var nota = $(this).attr('alt');
		var items = nota.split(";");
		var numero = items[0];
		var emissao = items[1];
		notafiscalclick(numero, emissao);
	});

	$(document).on("click", "#_chkallnotes", function(event){
		$(".chknotes").each(function(i, item){
			if(item.checked === true){
				item.checked = false;
			}else{
				item.checked = true;
			}
		});		    
	});

	$(document).on("click", "#_downloadcsvs", function(event){
		var form = $("#_formdownloadcsvs");
		fileDownload(form);
	});
	
</script>

<div class="center-align div-title-padrao col s12">
		Listagem das Notas Fiscais recebidas
</div>
<br><br>


<div id="formtabela" class="row">

<form action="srl" method="post" id="_formdownloadcsvs">

	<div class="col s12">
		<a href="srl?acao=notasfiscaisexcel"
			onclick="exportaConsultaExcel(e)"><img
			src="resources/images/export_excel.png" title="Exportar para Excel"
			style="float: left; margin-right: 1%"></a>
		<button type="button" id="_downloadcsvs" class="waves-effect waves-light btn blue">CSV</button>
	</div>
	
	<div class="row"></div>
	

	<input type="hidden" name="acao" value="exportallnotas">
	<c:if test="${!empty notasfiscais }">
	
		<table class="responsive-table highlight display datables-table-notas">
			<thead>
				<tr>					
					<th>Nº Nota</th>
					<th>Natureza [cfop]</th>					
					<th>Emissão</th>
					<th>Emitente</th>
					<th>Cnpj nº</th>
					<th>Municipio</th>
					<th>UF</th>
					<th>Valor Liquido</th>
					<th>Data Chegada</th>
					<th>Nº Pedido</th>
					<th>	
							<input type="checkbox" class="filled-in form-control" id="_chkallnotes">
							<label for="_chkallnotes">CSV</label>					
					</th>
					<th>#</th>					
				</tr>
			</thead>
			<tbody>
				<c:forEach var="nota" items="${notasfiscais }">
					<tr>						
						<td><a href="javascript:void(0)" id="detalhenotafiscal"
							alt='${nota.idnota};${nota.emissao}'>${nota.idnota}</a></td>
						<td>${nota.natop} [${nota.cfop}]</td>	
						<td><fmt:formatDate value="${nota.emissao}"
								pattern="dd/MM/yyyy" /></td>
						<td>${nota.emitente }</td>
						<td>${nota.cnpjemit }</td>
						<td>${nota.municipio }</td>
						<td>${nota.UF }</td>
						<td><fmt:formatNumber
								value="${nota.liquido}" type="number" pattern="#,#00.00#" /></td>
						<td><fmt:formatDate value="${nota.chegada}"
								pattern="dd/MM/yyyy" /></td>
						<td><a href="#!"
							onclick="enviardetalhepedidoclick('${nota.idpedido}')">${nota.idpedido}</a></td>
						<td>
							<input type="checkbox" class="filled-in chknotes" name="_notas-id" value="${nota.idnota}" id="check${nota.idnota}">
							<label for="check${nota.idnota}">.</label>
						</td>								
						<td>
							<a class="btn-floating btn-small waves-effect waves-light red noPrint" 
							title="Excluir nota fiscal" href="javascript:void(0)"
							onclick="deletarNotaFiscalClick('${nota.idnota}')">
							<i class="material-icons">delete_forever</i></a>											
						</td>
					</tr>
					<input type="checkbox" name="_cnpj-id" value="${nota.cnpjemit}" style="opacity:0; position:absolute; left:9999px" checked>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th colspan="12" class="right-align"></th>
				</tr>
			</tfoot>
		</table>
		<hr>
	</c:if>
	
</form>

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


</div>
<!-- Fim da div formtabela -->

