
<script type="text/javascript">
	
	$(document).ready(function() {
	
		$("#tableadocoesdoproduto").dataTable({
			"bPaginate": true,
	        "sDom":'fptip'
		});
	});
	
	</script>

<div id="divcontent">
	<table id="tableadocoesdoproduto">
		<c:choose>
			<c:when test="${!empty doacaoProd}">

				<tr class="rows">
					<th>Emissao</th>
					<th>Vendedor</th>
					<th>Escola</th>
					<th>Professor</th>
					<th>Qtde</th>
					<th>#</th>
				</tr>

				<c:set var="count" value="0"></c:set>
				<c:set var="aux" value="0"></c:set>
				<c:forEach var="p" items="${doacaoProd}">
					<c:set var="count" value="${count + p.qtde}"></c:set>
					<c:set var="aux" value="${aux + 1}"></c:set>
					<tr class="rows" style="font-size: 9pt">
						<td><fmt:formatDate value="${p.emissao}" pattern="dd/MM/yyyy" /></td>
						<td>${p.vendedor}<br>
						<td>${p.escola}</td>
						<td>${p.professor}</td>
						<td>${p.qtde}</td>
						<td><a href="javascript:void(0)" class="modal-action modal-close"
							onclick="doacaoPrint(${p.iddoacao})">Detalhar</a></td>
					</tr>
				</c:forEach>
				<tr style="font-weight: bold; font-size: 9pt">
					<td colspan="3">${aux}DOACOES&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp; QUANTIDADE TOTAL ------------------------></td>
					<td>${count}</td>
					<td></td>
				</tr>
			</c:when>

			<c:otherwise>
				<h3>Item sem doações cadastradas!!!</h3>
			</c:otherwise>

		</c:choose>
	</table>
</div>