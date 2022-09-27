<div id="divcontent">

	<c:set var="escolas" value="${roteiroescolas}"></c:set>

	<label style="text-align: left; font-weight: bold;">${fn:length(escolas)}
		escola(s)</label> <a href="javascript:void(0)"
		onclick="detalhesalvarroteiroclick()"> <img
		src="resources/images/save.png" title="Salvar">
	</a> <a href="javascript:void(0)" onclick="cancelarroteiroclick()"> <img
		src="resources/images/cancel.png" title="Excluir">
	</a>

</div>
