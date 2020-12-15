<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div id="divcontent">
	<table>
		<c:choose>
			<c:when test="${!empty pedProd}">

				<tr>
					<th>Nº Pedido</th>
					<th>Data</th>
					<th>Qt.Pedido</th>
					<th>Previsão</th>
					<th colspan="4">[N.Fiscal] -- [Dt.Emissão] -- [Qtde] --
						[Dt.Chegada]</th>
					<th>Pendente</th>
					<th>#</th>
				</tr>

				<c:forEach var="p" items="${pedProd}">

					<c:forEach var="itempedido" items="${p.itens}">

						<c:set var="url"
							value="srl?acao=detalharped&idpedido=${p.idpedido}" />

						<c:if test="${itempedido.cancelado eq 1}">
							<c:set var="bgcor" value="d1"></c:set>
						</c:if>
						<c:if test="${itempedido.cancelado eq 0}">
							<c:set var="bgcor" value=""></c:set>
						</c:if>

						<tr class="rows ${bgcor}"
							style="font-family: tahoma; font-size: 8pt;">
							<c:choose>
								<c:when
									test="${!empty usuariologado and usuariologado.cargo eq 1}">
									<td><a href="#!"
										onclick="enviardetalhepedidoclick('${p.idpedido}')">${p.idpedido}</a></td>
								</c:when>
								<c:otherwise>
									<td>${p.idpedido}</td>
								</c:otherwise>
							</c:choose>
							<td><fmt:formatDate value="${p.data}" pattern="dd/MM/yyyy" /></td>
							<td>${itempedido.qtdpedida}</td>
							<td><fmt:formatDate value="${itempedido.previsao}"
									pattern="dd/MM/yyyy" /></td>
							<td colspan="4"><c:forEach var="nnota"
									items="${itempedido.notas}">
										[<a href="javascript:void(0)" id="detalhenotafiscal"
										alt="${nnota.idnota};${nnota.emissao}">${nnota.UF}-${nnota.idnota}</a>] -- 
										[<fmt:formatDate value="${nnota.emissao}" pattern="dd/MM/yyyy" />] -----										 
										[${nnota.qtdtotal}] --- 
										[<fmt:formatDate value="${nnota.chegada}" pattern="dd/MM/yyyy" />]<br />
								</c:forEach></td>
							<td>${itempedido.qtdpendente}</td>
							<td><span><a href="#!" alt="${p.idpedido};${itempedido.item.codigo}"
									id="obsitempedido">Observação</a></span></td>
						</tr>
					</c:forEach>

				</c:forEach>

			</c:when>

			<c:otherwise>
				<h3>Item sem pedidos cadastrados!!!</h3>
			</c:otherwise>

		</c:choose>
	</table>
</div>