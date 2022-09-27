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

<title>${pageTitle}-Doação pesquisa</title>
</head>
<body onload="alternate('tabela')">

	<c:import url="headerTop.jsp"></c:import>

	<c:if test="${!empty professor}">

		<table border="none" id="tabela">

			<c:set var="registros" value="${fn:length(professor)}"></c:set>
			<p style="margin: auto; text-align: center; color: red;">${registros}
				registros recuperados...</p>

			<tr>
				<th>Nome</th>
			</tr>
			<c:forEach var="prof" items="${professor}">

				<tr style="font-weight: bold;">
					<td><a href="srl?acao=doacaoimprimir&idprofessor=${prof.id}">${prof.nome}</a></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:import url="headerFooter.jsp"></c:import>
</body>
</html>