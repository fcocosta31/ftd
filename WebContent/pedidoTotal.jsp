
<div id="divcontent">

	<label
		style="color: white; text-align: left; font-weight: bold;">${fn:length(pedcliente.itens)}
		itens</label> <label style="text-align: left; font-weight: bold;"> <fmt:formatNumber
			value="${pedcliente.total}" type="number" pattern="#,#00.00#" />
	</label>
	<a href="javascript:void(0)"
		onclick=""javascript:location.href='pedClienteRegistrar.jsp'""> <img
		src="resources/images/eye.png" title="Ver pedido" width="32px" height="auto">
	</a>

</div>

