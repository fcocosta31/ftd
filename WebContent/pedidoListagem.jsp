<div class="center-align div-title-padrao col s12">
		Listagem dos pedidos efetuados ao Fornecedor
</div>
<br><br>

<div class="container">

	<div class="row">

		<c:set var="ped" value="${listapedidos}" />

		<c:choose>
			<c:when test="${!empty ped}">

				<table class="highlight responsive-table datables-table">
					<thead>				
						<tr>
							<th>Nº.Pedido</th>
							<th>Nº.Itens</th>
							<th>Qt.Total</th>
							<th>Data</th>
							<th>#</th>
						</tr>
					</thead>
					
					<tbody>
					
						<c:forEach var="pedido" items="${listapedidos}">
					
							<tr class="rows">
								<td><a href="#!"
									onclick="enviardetalhepedidoclick('${pedido.idpedido}')">${pedido.idpedido}</a></td>
								<td>${pedido.qtitens}</td>
								<td>${pedido.qttotal}</td>
								<td><fmt:formatDate
										value="${pedido.data}" pattern="dd/MM/yyyy" /></td>
								<td>
									<a class="btn-floating btn-small waves-effect waves-light red modal-trigger" 
									title="Excluir pedido" href="javascript:void(0)"
									onclick="deletarPedidoClick('${pedido.idpedido}')">
									<i class="material-icons">delete_forever</i></a>
								</td>
							</tr>
	
						</c:forEach>
					
					</tbody>
					<tfoot>	
						<tr>
							<td colspan="5"></td>
						</tr>
					</tfoot>
				</table>

			</c:when>

			<c:otherwise>
				<h4>Sem pedidos nessa faixa de datas!</h4>
			</c:otherwise>
		</c:choose>
	</div>

</div>
