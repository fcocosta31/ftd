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

<script>
	$(document).ready(function(){
		$("#editarproduto").validate({
			rules: {
				txtcodbar: {required: false,ean13: true}								
			}	
		});
	});
	</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Editar dados do Produto</title>
</head>
<body onload="alternate('tabela')">
	<div id="corpopop">

		<h1>Alterar dados do produto</h1>
		<form action="srl" method="post" id="editarproduto">
			<fieldset id="fieldedit">
				<legend>Dados do Produto</legend>

				<c:set var="produto" value="${dadosprd_servlet}" />

				<label>Código: </label> <input type="text" value="${produto.codigo}"
					name="txtcodigo" size="10" readonly class="text"> <label>Descrição:
				</label> <input type="text" value="${produto.descricao}" name="txtdescricao"
					size="62" class="text"><br /> <br /> <label>Autor:
				</label> <input type="text" value="${produto.autor}" name="txtautor"
					size="60" class="text"> <label>CodBarras: </label><input
					type="text" value="${produto.codbar}" name="txtcodbar"
					id="txtcodbar" size="13" class="text"><br /> <br /> <label>Nivel:
				</label> <input type="text" value="${produto.nivel}" name="txtnivel"
					size="15" class="text"> <label>Coleção: </label> <input
					type="text" name="txtcolecao" value="${produto.colecao}" size="61"
					class="text"><br /> <br /> <label>Familia: </label> <input
					type="text" name="txtfamilia" value="${produto.familia}" size="15"
					class="text"> <label>Obs/Localização: </label> <input
					type="text" value="${produto.obs}" name="txtobs" size="50"
					class="text"><br /> <br /> <label>Preço: </label> <input
					type="text" name="txtpreco"
					value="<fmt:formatNumber value="${produto.preco}" type="number" pattern="#,#00.00#"/>"
					size="10" class="text"> <br /> <br /> <input
					type="hidden" name="acao" value="editarprd"> <input
					class="button white" type="button" value="Voltar"
					onclick="history.go(-1)"> <input class="button white"
					type="submit" value="Enviar">
			</fieldset>
		</form>
	</div>
</body>
</html>