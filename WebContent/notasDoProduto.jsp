<div id="divcontent">

<div id="formtabela" class="row">

	<div class="col s12">
		<a href="srl?acao=notasfiscaisexcel"
			onclick="exportaConsultaExcel(e)"><img
			src="resources/images/export_excel.png" title="Exportar para Excel"
			style="float: left; margin-right: 1%"></a>		
	</div>
	<div class="row"></div>

	<c:if test="${!empty notasProd }">

		<table class="responsive-table highlight display datables-table" id="tablenotasfiscais">
			<thead>
				<tr>
					<th>Nº Nota</th>
					<th>Natureza [cfop]</th>					
					<th>Emissão</th>
					<th>Emitente</th>
					<th>Municipio</th>
					<th>UF</th>
					<th>Valor Liquido</th>
					<th>Data Chegada</th>
					<th>Nº Pedido</th>
					<th>Qtde</th>
					<th>Preço Unit.</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="nota" items="${notasProd}">
					<tr>
						<td><a href="javascript:void(0)" id="detalhenotafiscal"
							alt='${nota.idnota};${nota.emissao}'>${nota.idnota}</a></td>
						<td>${nota.natop} [${nota.cfop}]</td>	
						<td><fmt:formatDate value="${nota.emissao}"
								pattern="dd/MM/yyyy" /></td>
						<td>${nota.emitente }</td>
						<td>${nota.municipio }</td>
						<td>${nota.UF }</td>
						<td><fmt:formatNumber
								value="${nota.liquido}" type="number" pattern="#,#00.00#" /></td>
						<td><fmt:formatDate value="${nota.chegada}"
								pattern="dd/MM/yyyy" /></td>
						<td><a href="#!"
							onclick="enviardetalhepedidoclick('${nota.idpedido}')">${nota.idpedido}</a></td>
						<td>${nota.itens[0].quantidade }</td>
						<td><fmt:formatNumber
								value="${nota.itens[0].preco}" type="number" pattern="#,#00.00#" /></td>
									
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9"></td>
				</tr>
			</tfoot>
		</table>
		<hr>
	</c:if>

</div>
</div>