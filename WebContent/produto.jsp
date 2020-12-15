<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript" src="javascript/jquery.js"></script>
<script type="text/javascript" src="javascript/jquery.validate.js"></script>
<script type="text/javascript" src="javascript/scripts.js"></script>

<link href="style/estilo.css" rel="stylesheet" type="text/css">

<title>${pageTitle}-Consulta produto</title>
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
		<table border="1" id="tabelapop">
			<c:choose>
				<c:when test="${!empty pedido}">

					<tr>
						<th>Nº Pedido</th>
						<th>Data</th>
						<th>Qtde</th>
						<th>Previsão</th>
						<th>Dt.Chegada</th>
						<th>N.Fiscal</th>
						<th>Qtde</th>
					</tr>

					<c:forEach var="p" items="${pedido}">

						<c:forEach var="itempedido" items="${p.itens}">

							<c:set var="url"
								value="srl?acao=detalharped&idpedido=${p.idpedido}" />

							<tr style="font-family: tahoma; font-size: 10pt;">
								<c:choose>
									<c:when test="${!empty usuariologado}">
										<td><a href="${url}">${p.idpedido}</a></td>
									</c:when>
									<c:otherwise>
										<td>${p.idpedido}</td>
									</c:otherwise>
								</c:choose>
								<td><fmt:formatDate value="${p.data}" pattern="dd/MM/yyyy" /></td>
								<td>${itempedido.qtdepedida}</td>
								<td><fmt:formatDate value="${itempedido.previsao}"
										pattern="dd/MM/yyyy" /></td>
								<td>${itempedido.chegada}</td>
								<td>${itempedido.notafiscal}</td>
								<td><c:set var="qttotal" value="0"></c:set> <c:forEach
										var="qtdec" items="${itempedido.qtdechegou}">
										<c:set var="qttotal" value="${qttotal + qtdec}"></c:set>
									</c:forEach> ${qttotal}</td>
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
	<c:import url="headerFooter.jsp"></c:import>
</body>
</html>