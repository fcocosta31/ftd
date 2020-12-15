<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="javascript/jquery.js"></script>
<script type="text/javascript" src="javascript/jquery.validate.js"></script>
<script type="text/javascript" src="javascript/scripts.js"></script>

<link href="style/estilo.css" rel="stylesheet" type="text/css">

<title>${pageTitle}-Detalhar Adoções</title>
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
		<fieldset id="fieldorcam">
			<c:set var="itens" value="${fn:length(orcamento.itens)}" />
			<h2 style="font-size: 16pt;">${orcamento.escola.nome}</h2>
		</fieldset>
		<div class="noPrint">
			<input style="margin-left: 45%" class="button white" type="button"
				value="Imprimir" onclick="window.print()"> <input
				style="margin: auto" class="button white" type="button"
				value="Voltar" onclick="history.go(-1)">
		</div>
	</form>

	<table border="1" id="tabela">

		<tr>
			<th id="thcodigo">Código</th>
			<th width="350px">Descrição</th>
			<th id="thpreco">Preço</th>
			<th id="thestoque">Qtde</th>
			<th id="thpreco">Total</th>
			<th id="thestoque">Estq</th>
			<th>......</th>
		</tr>

		<c:forEach var="pd" items="${orcamento.itens}">

			<c:set var="url"
				value="srl?acao=consultarprd&codigo=${pd.produto.codigo}" />

			<tr>
				<td><a href="${url}"
					onclick="javascript:void window.open('${url}','1381922944901','width=900,height=600,toolbar=0,menubar=0,location=0,status=1,scrollbars=1,resizable=1,left=0,top=0');return false;">${pd.produto.codigo}</a></td>
				<td width="350px">${pd.produto.descricao}</td>
				<td style="text-align: right"><fmt:formatNumber
						value="${pd.produto.preco}" type="number" pattern="#,#00.00#" /></td>
				<td style="text-align: right">${pd.quantidade}</td>
				<td style="text-align: right"><fmt:formatNumber
						value="${pd.quantidade * pd.produto.preco}" type="number"
						pattern="#,#00.00#" /></td>
				<td style="text-align: right">${pd.produto.estoque}</td>
				<td><a href="srl?acao=remitem&codigoitem=${pd.produto.codigo}">remover</a></td>
			</tr>

		</c:forEach>
		<tr>
			<td colspan="2" style="text-align: center; font-weight: bold;">${itens}
				itens</td>
			<td style="text-align: right; font-weight: bold;">Totais</td>
			<td style="text-align: right; font-weight: bold;">${orcamento.qtdtotal}</td>
			<td colspan="2" style="text-align: center; font-weight: bold;"><fmt:formatNumber
					value="${orcamento.total}" type="number" pattern="#,#00.00#" /></td>
			<td>......</td>
		</tr>
		<tr>
			<td colspan="2"></td>
			<td style="text-align: center; font-weight: bold;" colspan="2">%
				Desc.:<input type="text" size="2" id="txtdesconto" value="0"
				style="text-align: right; font-weight: bold; font-family: arial; font-size: 8pt;"
				onkeydown="setDesconto(this, ${orcamento.total}, event)">
			</td>
			<td colspan="2" style="text-align: center; font-weight: bold;"
				id="tdtotal"></td>
		</tr>

	</table>
	<c:import url="headerFooter.jsp"></c:import>
</body>
</html>