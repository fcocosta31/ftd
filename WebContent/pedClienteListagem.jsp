<div class="center-align div-title-padrao col s12">
		Listagem dos pedidos de Clientes
</div>

<div class="row">
  <div class="container">

	<c:choose>
		<c:when test="${!empty listapedcliente}">

			<table class="highlight datables-table"
				id="tablepedcliente">
				<thead>
					<tr>
						<th>No.Ped.</th>
						<th>Cod.Cliente</th>
						<th>Nome</th>
						<th>Data</th>
						<th>Situação</th>
						<th class="col l3 m3 s3">#</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="pedido" items="${listapedcliente}">

						<tr>
							<td>${pedido.idpedido}</td>
							<td>${pedido.cliente.codigoftd}</td>
							<td style="text-align: left">${pedido.cliente.razaosocial}</td>
							<td><fmt:formatDate
									value="${pedido.emissao}" pattern="dd/MM/yyyy" /></td>
							<td>${pedido.situacao}</td>
							<td>

							<div class="row">
							<div class="col l4 m4 s12">
							<a href="srl?acao=detalharpedcliente&idpedido=${pedido.idpedido}" 
							   class="btn-floating btn-small waves-effect waves-light blue noPrint"
							   title="Detalhar pedido">
							   <i class="material-icons">visibility</i></a>
							</div>												
							<div class="col l4 m4 s12">
							<a href="srl?acao=pedclientependente&idpedido=${pedido.idpedido}" 
							   class="btn-floating btn-small waves-effect waves-light grey noPrint"
							   title="Detalhar pendência">
							   <i class="material-icons">visibility off</i></a>
							</div>
							<div class="col l4 m4 s12">												
							<a href="javascript:void(0)" onclick="deletepedcliente('${pedido.idpedido}')" 
							   class="btn-floating btn-small waves-effect waves-light red noPrint"
							   title="Deletar pedido">
							   <i class="material-icons">delete forever</i></a>
							</div>
							</div>

							</td>
						</tr>

					</c:forEach>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="6"></td>
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

