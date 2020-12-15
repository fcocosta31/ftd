
<div id="divcontent">
	<table>
		<c:choose>
			<c:when test="${!empty kardex}">

				<tr class="rows">
					<th>Filial</th>
					<th>Nº Orcam.</th>
					<th>Nº Nota/Série</th>
					<th>Data</th>
					<th>Qt.Venda</th>
					<th>Codigo</th>
					<th colspan="4">Cliente</th>
					<th>Municipio</th>
					<th>#</th>
				</tr>

				<c:set var="count" value="0"></c:set>
				<c:set var="aux" value="0"></c:set>

				<c:forEach var="itemk" items="${kardex}">

					<c:set var="count" value="${count + itemk.qtde}"></c:set>
					<c:set var="aux" value="${aux + 1}"></c:set>

					<tr class="rows" style="font-family: tahoma; font-size: 8pt;">
						<td>${itemk.filial}</td>
						<td>${itemk.numero}</td>
						<td>${itemk.nota}/${itemk.serie}</td>
						<td><fmt:formatDate value="${itemk.emissao}"
								pattern="dd/MM/yyyy" /></td>
						<td>${itemk.qtde}</td>
						<td>${itemk.codigo}</td>
						<td colspan="4">${itemk.nome}</td>
						<td>${itemk.municipio}</td>
						<td><a href="javascript:void(0)" class="modal-action modal-close"
							onclick="detalhekardexclick('${itemk.filial}','${itemk.numero}','${itemk.codigo}','${itemk.nome}')"
							>Detalhar</a></td>
					</tr>

				</c:forEach>

				<tr style="font-weight: bold; font-size: 9pt">
					<td colspan="10">${aux}REGISTROS&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp; TOTAL VENDIDO NO ANO ------></td>
					<td>${count}</td>
					<td></td>
				</tr>

			</c:when>

			<c:otherwise>
				<h3>Item sem vendas cadastradas!!!</h3>
			</c:otherwise>

		</c:choose>
	</table>
</div>