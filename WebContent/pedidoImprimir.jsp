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
								Pend�ncia
							</span>	
						</div>
					</c:if>
						
					<table>
						<tr class="row">
							<th><br> <span
								style="font-weight: bold; font-size: 16pt">Planilha de separa��o</span>
									<br> 
							<span>N� Pedido: ${pedcliente.idpedido}</span><br></th>
							<th colspan="4"><br> <span
								style="font-weight: bold; font-size: 14pt">${pedcliente.cliente.codigoftd}
									- ${pedcliente.cliente.razaosocial}</span><br> <span
								style="font-weight: bold; font-size: 10pt">${pedcliente.cliente.endereco}
									/ ${pedcliente.cliente.municipio}-${pedcliente.cliente.uf}</span><br>
								<br></th>
							<th><span>Emiss�o: <fmt:formatDate
										value="${pedcliente.emissao}" pattern="dd/MM/yyyy" /></span></th>
						</tr>
					</table>
				
				</div>

				<br/>
				
				<c:set var="itens" value="${fn:length(pedcliente.itens)}" />
				<div class="row">
					<div class="col s12 bordered">
						<table style="font-size: 7pt">

							<tr class="border_bottom" style="color: white; background-color: #5599FF">
								<th>C�digo</th>
								<th style="text-align: center">Qt.Pedida</th>
								<th width="400px">Descri��o</th>
								<th style="text-align: center">Qt.Atendida</th>
								<th style="text-align: center">Qt.Pendente</th>
								<th style="text-align: center">Estoque</th>
							</tr>

							<c:forEach var="pd" items="${pedcliente.itens}">
								<tr class="border_bottom" style="font-size: 9pt">
									<td>${pd.item.codigo}</td>
									<td style="text-align: center; font-size: 11pt">${pd.qtdpedida}</td>
									<td width="400px">${pd.item.descricao}</td>
									<td style="text-align: center">${pd.qtdatendida}</td>
									<td style="text-align: center; font-size: 10pt">${pd.qtdpendente}</td>
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
							<tr class="rows">
								<td style="text-align: center; font-weight: bold;">Totais</td>
								<td style="text-align: center; font-weight: bold;">${pedcliente.qtdtotal}</td>
								<td style="text-align: center; font-weight: bold;">${itens}
									itens</td>
								<td style="text-align: center; font-weight: bold;">${pedcliente.qtdatendida}</td>
								<td style="text-align: center; font-weight: bold;">${pedcliente.qtdtotal - pedcliente.qtdatendida}</td>
								<td></td>
							</tr>

							<tr class="border_bottom">
								<td colspan="8"></td>
							</tr>

						</table>
						<hr>

						<div>
							<div style="text-align: left; font-size: 8pt;">Controle
								n� ${pedcliente.idpedido}</div>
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
