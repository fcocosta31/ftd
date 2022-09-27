<div id="divcontent">

	<div class="row">
		<span style="font-weight: bold">Número: </span>${notafiscal.idnota}
		
		<span style="font-weight: bold"> / Série: </span> <fmt:formatNumber
				value="${notafiscal.serie}" type="number" pattern="000" /><br>

		<span style="font-weight: bold">CFOP: </span>${notafiscal.cfop}
		
		<span style="font-weight: bold"> / Natureza: </span>${notafiscal.natop}<br>
		
		<span style="font-weight: bold">Emitente: </span>
		${notafiscal.emitente} - ${notafiscal.municipio}/${notafiscal.UF}<br>
		
		<span style="font-weight: bold">Emissão: </span> <fmt:formatDate
				value="${notafiscal.emissao}" pattern="dd/MM/yyyy" /><br>
		
		<span style="font-weight: bold">Valor Bruto: </span> <fmt:formatNumber
				value="${notafiscal.total}" type="number" pattern="#,#00.00#" /> - 
		<span style="font-weight: bold">Desconto (<fmt:formatNumber
				value="${notafiscal.percentual * 100}" type="number" pattern="#,#00" />%):
		</span> <fmt:formatNumber
				value="${notafiscal.desconto}" type="number" pattern="#,#00.00#" /> - 
		<span style="font-weight: bold">Valor Liquido: </span> <fmt:formatNumber
				value="${notafiscal.liquido}" type="number" pattern="#,#00.00#" /><br>
		
		<span style="font-weight: bold">Nº Pedido: </span>
			${notafiscal.idpedido}
		
		<span style="font-weight: bold">
				/ Data Chegada: </span> <fmt:formatDate value="${notafiscal.chegada}"
				pattern="dd/MM/yyyy" />
	</div>

	<table class="striped responsive-table">

		<tr>
			<th>Código</th>
			<th>Descrição</th>
			<th>Preço</th>
			<th>Qtde</th>
			<th>Total</th>
		</tr>

		<c:forEach var="pd" items="${notafiscal.itens}">

			<tr style="font-size: 8pt">
				<td>${pd.item.codigo}</td>
				<td>${pd.item.descricao}</td>
				<td><fmt:formatNumber value="${pd.preco}" type="number"
						pattern="#,#00.00#" /></td>
				<td><fmt:formatNumber value="${pd.quantidade}" type="number"
						pattern="0" /></td>
				<td><fmt:formatNumber value="${pd.total}" type="number"
						pattern="#,#00.00#" /></td>
			</tr>

		</c:forEach>
	</table>
</div>