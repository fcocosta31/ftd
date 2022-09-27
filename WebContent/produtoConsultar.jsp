<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link href="resources/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript" src="resources/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>


<script type="text/javascript" src="resources/js/jquery.js"></script>
<script type="text/javascript" src="resources/js/jquery.validate.js"></script>
<script type="text/javascript" src="resources/js/scripts.js"></script>

<link href="resourses/css/estilo.css" rel="stylesheet" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>${pageTitle}-Consulta ao produto</title>
</head>
<body onload="alternate('tabelapop')">
	<div id="corpopop">

		<c:set var="cp" value="${consprd_servlet}" />
		<c:set var="item" value="${cp.item}" />
		<c:set var="pedido" value="${cp.pedidos}" />
		<c:set var="url" value="srl?acao=dadosprd&codigo=${item.codigo}" />
		<c:choose>
			<c:when test="${!empty usuariologado and usuariologado.cargo eq 1}">
				<h4>
					<a href="${url}">${item.codigo} - ${item.descricao}</a>
				</h4>
			</c:when>
			<c:otherwise>
				<h4>${item.codigo}-${item.descricao}</h4>
			</c:otherwise>
		</c:choose>

		<form>
			<fieldset id="fieldpop">
				<legend>Dados do Produto</legend>
				<label><span style="font-weight: bold">Autor: </span>
					${item.autor}</label><br /> <label><span style="font-weight: bold">Familia:
				</span> ${item.familia} / </label> <label><span style="font-weight: bold">Nivel:
				</span> ${item.nivel}</label><br /> <label><span style="font-weight: bold">CodBarras:
				</span> ${item.codbar} / </label> <label><span style="font-weight: bold">Obs./Localização:
				</span> ${item.obs}</label> <br /> <br /> <input class="button white"
					type="button" value="Fechar" onclick="fechaForm()">
			</fieldset>
		</form>
		<br /> <br />
		<h2>Pedidos</h2>
		<table border="1" id="tabelapop">
			<c:choose>
				<c:when test="${!empty pedido}">

					<tr>
						<th>Nº Pedido</th>
						<th>Data</th>
						<th>Qt.Pedido</th>
						<th>Previsão</th>
						<th>N.Fiscal</th>
						<th>Dt.Emissão</th>
						<th>Qtde</th>
						<th>Dt.Chegada</th>
					</tr>

					<c:forEach var="p" items="${pedido}">

						<c:forEach var="itempedido" items="${p.itens}">

							<c:set var="url"
								value="srl?acao=detalharped&idpedido=${p.idpedido}" />

							<tr style="font-family: tahoma; font-size: 8pt;">
								<c:choose>
									<c:when test="${!empty usuariologado}">
										<td><a href="${url}">${p.idpedido}</a></td>
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
										[<a href="srl?acao=detalharnotafiscal&idnota=${nnota.idnota}">${nnota.UF}-${nnota.idnota}</a>] -- 
										[<fmt:formatDate value="${nnota.emissao}" pattern="dd/MM/yyyy" />] -- 
										[${nnota.qtdtotal}] -- 
										[<fmt:formatDate value="${nnota.chegada}" pattern="dd/MM/yyyy" />]<br />
									</c:forEach></td>
							</tr>
						</c:forEach>

					</c:forEach>

				</c:when>

				<c:otherwise>
					<h3>Item sem pedidos cadastrados!!!</h3>
				</c:otherwise>

			</c:choose>
		</table>

		<br />

		<h2>Adoções</h2>
		<table border="1" id="tabelapop">
			<tr>
				<th>Ano</th>
				<th>Escola</th>
				<th>Municipio</th>
				<th>Série</th>
				<th>Qt.Adotada</th>
			</tr>

			<c:set var="itens" value="0"></c:set>
			<c:set var="totaladotado" value="0"></c:set>

			<c:forEach var="adocao" items="${cp.adocoes}">

				<c:set var="itens" value="${itens + 1}"></c:set>
				<c:set var="totaladotado" value="${totaladotado + adocao.qtde}"></c:set>

				<tr style="font-family: tahoma; font-size: 8pt;">
					<td>${adocao.ano}</td>
					<td width="300">${adocao.nomeescola}</td>
					<td width="150">${adocao.municipio}</td>
					<td>${adocao.serie}</td>
					<td style="text-align: center;">${adocao.qtde}</td>
				</tr>
			</c:forEach>

			<tr
				style="font-family: tahoma; font-weight: bold; font-size: 8pt; text-align: center;">
				<td colspan="4">Total de ${itens} escolas</td>
				<td>${totaladotado}</td>
			</tr>

		</table>

	</div>
	<c:import url="headerFooter.jsp"></c:import>
</body>
</html>