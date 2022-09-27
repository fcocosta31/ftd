<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="javascript/jquery.dataTables.min.js"></script>

<link href="style/jquery.dataTables.css" rel="stylesheet"
	type="text/css">
<link href="style/estilo.css" rel="stylesheet" type="text/css">

<title>${pageTitle}-Demanda</title>
</head>
<body onload="alternate('tabela')">

	<c:choose>
		<c:when test="${!empty usuariologado}">
			<c:import url="headerLogado.jsp"></c:import>
		</c:when>
		<c:otherwise>
			<c:import url="headerLogin.jsp"></c:import>
		</c:otherwise>
	</c:choose>

	<form>
		<fieldset id="fieldedit">

			<h1>Demanda de livros</h1>
			<input class="button white" type="button" value="Voltar"
				onclick="history.go(-1)" style="margin-left: 40%"> <input
				class="button white" type="button" value="Imprimir"
				onclick="window.print()">

		</fieldset>
	</form>

	<br />
	<br />

	<table border="1" id="tabela">

		<thead>
			<tr>
				<th id="thcodigo">Código</th>
				<th id="thdescricao">Descrição</th>
				<th id="thestoque">Demanda</th>
				<th id="thestoque">Pedido</th>
				<th id="thestoque">Pendente</th>
				<th id="thestoque">Estoque</th>
				<th id="thestoque">Vendido</th>
				<th id="thestoque">Pedir</th>
				<th id="thestoque">Escolas</th>
			</tr>
		</thead>
		<c:set var="totaldemanda" value="0"></c:set>
		<c:set var="totalpedido" value="0"></c:set>
		<c:set var="totalpendente" value="0"></c:set>
		<c:set var="totalestoque" value="0"></c:set>
		<c:set var="totalvendido" value="0"></c:set>
		<c:set var="totalnecessidade" value="0"></c:set>
		<c:set var="totalitens" value="0"></c:set>


		<c:forEach var="t" items="${listademanda}">

			<c:set var="totaldemanda" value="${totaldemanda + t.demanda}"></c:set>
			<c:set var="totalpedido" value="${totalpedido + t.pedido}"></c:set>
			<c:set var="totalpendente" value="${totalpendente + t.pendente}"></c:set>
			<c:set var="totalestoque"
				value="${totalestoque + t.itemdemanda.item.estoque}"></c:set>
			<c:set var="totalvendido"
				value="${totalvendido + t.itemdemanda.item.qtdvendida}"></c:set>
			<c:set var="totalitens" value="${totalitens + 1}"></c:set>

			<c:set var="url"
				value="srl?acao=consultarprd&codigo=${t.itemdemanda.item.codigo}" />
			<tbody>
				<tr>
					<td><a href="${url}"
						onclick="javascript:void window.open('${url}','1381922944901','width=900,height=600,toolbar=0,menubar=0,location=0,status=1,scrollbars=1,resizable=1,left=0,top=0');return false;">${t.itemdemanda.item.codigo}</a></td>
					<td name="descricaoitem" width="430px">${t.itemdemanda.item.descricao}</td>
					<td style="text-align: right">${t.demanda}</td>
					<td style="text-align: right">${t.pedido}</td>
					<td style="text-align: right">${t.pendente}</td>
					<td style="text-align: right">${t.itemdemanda.item.estoque}</td>
					<td style="text-align: right">${t.itemdemanda.item.qtdvendida}</td>
					<c:set var="necessidade"
						value="${t.necessidade - (t.itemdemanda.item.estoque + t.itemdemanda.item.qtdvendida)}"></c:set>
					<c:if test="${necessidade < 0}">
						<c:set var="necessidade" value="0"></c:set>
					</c:if>
					<td style="text-align: right">${necessidade}</td>
					<td style="text-align: right">${t.escolas}</td>
				</tr>
			</tbody>
			<c:set var="totalnecessidade"
				value="${totalnecessidade + necessidade}"></c:set>

		</c:forEach>
		<tfoot>
			<tr>
				<td colspan="2" style="text-align: center">Total de itens:
					${totalitens}</td>
				<td style="text-align: center">${totaldemanda}</td>
				<td style="text-align: center">${totalpedido}</td>
				<td style="text-align: center">${totalpendente}</td>
				<td style="text-align: center">${totalestoque}</td>
				<td style="text-align: center">${totalvendido}</td>
				<td style="text-align: center">${totalnecessidade}</td>
				<td style="text-align: center">...</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>