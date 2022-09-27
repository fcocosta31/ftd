<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<script type="text/javascript">
	
	$(document).ready(function() {
	
		$("#tablependdoproduto").dataTable({
			"bPaginate": true,
	        "sDom":'fptip'
		});
	});
	
	</script>

<div id="divcontent">
	<table class="striped" id="tablependdoproduto">
		<c:choose>
			<c:when test="${!empty pendencias}">

				<tr class="rows">
					<th>Nº Ped.</th>
					<th>Emissao</th>
					<th>Codigo</th>
					<th>Cliente</th>
					<th>Qt.Pedida</th>
					<th>Qt.Atend</th>
					<th>Qt.Pend</th>
				</tr>

				<c:set var="countped" value="0"></c:set>
				<c:set var="countatn" value="0"></c:set>
				<c:set var="countpnd" value="0"></c:set>
				<c:set var="aux" value="0"></c:set>
				<c:forEach var="p" items="${pendencias}">
					<c:set var="countped" value="${countped + p.qtdpedida}"></c:set>
					<c:set var="countatn" value="${countatn + p.qtdatendida}"></c:set>
					<c:set var="countpnd" value="${countpnd + p.qtdpendente}"></c:set>
					<c:set var="aux" value="${aux + 1}"></c:set>
					<tr class="rows" style="font-size: 9pt">
						<td>${p.idpedido}<br>
						<td><fmt:formatDate value="${p.emissao}" pattern="dd/MM/yyyy" /></td>
						<td>${p.codigoftd}<br>
						<td><a
							href="srl?acao=detalharpedcliente&idpedido=${p.idpedido}">${p.nomeftd}</a></td>
						<td>${p.qtdpedida}</td>
						<td>${p.qtdatendida}</td>
						<td>${p.qtdpendente}</td>
					</tr>
				</c:forEach>
				<tr style="font-weight: bold; font-size: 9pt">
					<td colspan="3">${aux}REGISTROSRELACIONADOS</td>
					<td>TOTAIS</td>
					<td>${countped}</td>
					<td>${countatn}</td>
					<td>${countpnd}</td>
				</tr>
			</c:when>

			<c:otherwise>
				<h3>Item sem pendencias cadastradas!!!</h3>
			</c:otherwise>

		</c:choose>
	</table>
</div>