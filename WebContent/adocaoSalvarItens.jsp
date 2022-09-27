<div id="divcontent">
	<table class="bordered striped">

		<tr>
			<th>Descrição</th>
			<th>Série</th>
		</tr>

		<c:forEach var="it" items="${orcamento.itens}">

			<tr>
				<td><span style="font-size: 11pt; color: #000099;"><strong>${it.produto.descricao}</strong></span><br>
					<span style="color: gray; font-size: 8pt;">${it.produto.codigo}
						/ </span> <span style="color: gray; font-size: 8pt;">${it.produto.autor}
						/ </span> <span style="color: gray; font-size: 8pt;">${it.produto.obs}</span>
				</td>
				<td>${it.produto.serie}</td>
			</tr>
		</c:forEach>

	</table>
</div>