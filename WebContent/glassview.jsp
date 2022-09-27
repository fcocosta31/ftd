<c:choose>
<c:when test="${glass.consultor.setor eq 0}">
<div class="center-align div-title-padrao col s12">
		Posição consolidada dos consultores
</div>
<div class="container">
<h4>Setor: Todos</h4>
</div>
</c:when>
<c:otherwise>
<div class="center-align div-title-padrao col s12">
		Posição consolidada do consultor
</div>
<div class="container">
<h4>Setor: ${glass.consultor.setor} - ${glass.consultor.nome}</h4>
</div>
</c:otherwise>
</c:choose>
<div class="container">
<table class="striped">
		<thead>
		<tr>
			<th colspan="2">Informações gerais</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>Ano: </td>
			<td>${glass.year}</td>
		</tr>
		<tr>
			<td>Nº Escolas (com nº de alunos atualizados): </td>
			<td>${glass.escolas}</td>
		</tr>
		<tr>
			<td>Nº total de alunos: </td>
			<td>${glass.alunos}</td>
		</tr>
		<tr>
			<td>Nº de listas cadastradas: </td>
			<td>${glass.listas} --> <a href="srl?acao=verlistas" class="noPrint"
									title="Detalhar">detalhes <img src="resources/images/more.png" width="24px" height="auto"></a></td>
		</tr>
		</tbody>
</table>		
<table class="striped">		
		<thead>
		<tr>
			<th colspan="2">Média de livros adotados (família comercial)</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="md" items="${glass.medias}">
			<fmt:formatNumber var="roundedMedia" maxFractionDigits="1" type="number" pattern="#,##0.0#" value="${md.media}" />
			<tr>
				<td>${md.familia} </td>
				<td>${roundedMedia}</td>
			</tr>
		</c:forEach>
		</tbody>
</table>		
<table>		
		<thead>
		<tr>
			<th colspan="2">Cálculo do MarketShare por simulação</th>
		</tr>
		</thead>
		<tbody>
			<fmt:formatNumber var="fmarket" maxFractionDigits="2" type="number" pattern="#,##0.0#" value="${glass.marketshare}" />
			<tr>
				<td>MarketShare</td>
				<td>${fmarket}%</td>
			</tr>
		</tbody>

</table>
</div>