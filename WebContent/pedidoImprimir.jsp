<style>
@media all {
	.page-break {
		display: none;
	}
}

@media print {
	.page-break {
		display: block;
		page-break-before: avoid;
	}
}
</style>

<script type="text/javascript">

	$(document).ready(function(){
		window.print();
	});

</script>

<div class="row">

	<div class="col m1 text-left noPrint" id="divmenulateral"></div>

	<div class="col m9">

		<div id="formtabela">


			<div class="modal">
				<!-- Place at bottom of page -->
			</div>


			<div class="row" id="divlistagemescolas">

				<div class="bordered">
					
					<c:if test="${fn:containsIgnoreCase(pedcliente.situacao, 'PARCIALMENTE')}">
						<div class="row" style="font-weight: bold; font-size: 16pt; text-align: center;">						
							<span>
								Pendência
							</span>	
						</div>
					</c:if>
						
					<table>
						<tr class="row">
							<th><br> <span
								style="font-weight: bold; font-size: 16pt">Planilha de separação</span>
									<br> 
							<span>Nº Pedido: ${pedcliente.idpedido}</span><br></th>
							<th colspan="4"><br> <span
								style="font-weight: bold; font-size: 14pt">${pedcliente.cliente.codigoftd}
									- ${pedcliente.cliente.razaosocial}</span><br> <span
								style="font-weight: bold; font-size: 10pt">${pedcliente.cliente.endereco}
									/ ${pedcliente.cliente.municipio}-${pedcliente.cliente.uf}</span><br>
								<br></th>
							<th><span>Emissão: <fmt:formatDate
										value="${pedcliente.emissao}" pattern="dd/MM/yyyy" /></span></th>
						</tr>
					</table>
				
				</div>

				<br/>
				
				<c:set var="itens" value="${fn:length(pedcliente.itens)}" />
				<div class="row">
					<div>
						<table style="font-size: 8pt;" border="1" cellspacing="0" cellpadding="1">

							<tr class="row">
								<th>Código</th>
								<th style="text-align: center">Qt.Pedida</th>
								<th width="400px">Descrição</th>
								<th style="text-align: center">Qt.Atendida</th>
								<th style="text-align: center">Qt.Pendente</th>
								<th style="text-align: center">Estoque</th>
							</tr>

							<c:forEach var="pd" items="${pedcliente.itens}">
								<tr class="row" style="font-size: 11pt;">
									<td>${pd.item.codigo}</td>
									<td style="text-align: center; font-size: 11pt">${pd.qtdpedida}</td>
									<td width="400px">${pd.item.descricao}</td>
									<td style="text-align: center">
										<c:if test="${pd.qtdatendida > 0}">
											${pd.qtdatendida}
										</c:if>
									</td>
									<td style="text-align: center; font-size: 11pt">
											${pd.qtdpendente}
									</td>
									<td style="text-align: center"><c:choose>
											<c:when test="${!empty usuariologado}">
											${pd.item.estoque}
										</c:when>
											<c:otherwise>
											${pd.item.nivelestoque}
										</c:otherwise>
										</c:choose></td>
								</tr>

							</c:forEach>
							<tr class="row">
								<td style="text-align: center; font-weight: bold;">Totais</td>
								<td style="text-align: center; font-weight: bold;">${pedcliente.qtdtotal}</td>
								<td style="text-align: center; font-weight: bold;">${itens}
									itens</td>
								<td style="text-align: center; font-weight: bold;">${pedcliente.qtdatendida}</td>
								<td style="text-align: center; font-weight: bold;">${pedcliente.qtdtotal - pedcliente.qtdatendida}</td>
								<td></td>
							</tr>

							<tr>
								<td colspan="8"></td>
							</tr>

						</table>
						<hr>

						<div>
							<div style="text-align: left; font-size: 8pt;">Controle
								nº ${pedcliente.idpedido}</div>
							<br /> <br /> <label>Separado por:
								_____________________________________ Data:
								_____/_____/_________</label> <br /> <br /> <label>Conferido
								por: ________________________________________________________</label>
						</div>

					</div>
				</div>

			</div>
		</div>
	</div>
</div>
