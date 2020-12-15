<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div id="divcontent">
	<table id="tableadocoesdoproduto">
		<c:choose>
			<c:when test="${!empty adocaoProd}">

				<tr class="rows">
					<th>Ano</th>
					<th>Escola</th>
					<th>Vendedor</th>
					<th>Série</th>
					<th>Qt.Alunos</th>
					<th>#</th>
				</tr>

				<c:set var="count" value="0"></c:set>
				<c:set var="aux" value="0"></c:set>
				<c:forEach var="p" items="${adocaoProd}">
					<c:set var="count" value="${count + p.qtde}"></c:set>
					<c:set var="aux" value="${aux + 1}"></c:set>
					<tr class="rows" style="font-size: 9pt">
						<td>${p.ano}</td>
						<td><span style="font-weight: bold">${p.nomeescola}</span><br>
							<span style="font-size: 7pt">${p.escola.endereco} -
								${p.escola.bairro} &nbsp;&nbsp;&nbsp;
								${p.escola.municipio}-${p.escola.uf} &nbsp;&nbsp;&nbsp;</span></td>
						<td>${p.vendedor.nome}</td>
						<td>${p.serie}</td>
						<td>${p.qtde}</td>
						<td><a href="javascript:void(0)" onclick="detalheadocaoclick('${p.idescola}','${p.serie}','${p.ano}')"
							class="modal-action modal-close">Detalhar</a></td>
					</tr>
				</c:forEach>
				<tr style="font-weight: bold; font-size: 9pt">
					<td colspan="4">${aux}REGISTROS&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp; QUANTIDADE TOTAL DE ALUNOS (DEMANDA) ------></td>
					<td>${count}</td>
					<td></td>
				</tr>
			</c:when>

			<c:otherwise>
				<h3>Item sem adoções cadastradas!!!</h3>
			</c:otherwise>

		</c:choose>
	</table>
</div>