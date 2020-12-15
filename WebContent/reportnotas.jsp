<div class="center-align div-title-padrao col s12">
		Relatório de Orçamentos (Totvs)
</div>
<br><br>
<div class="container">

	<div class="row">
		
		<div class="col s12">
			<a href="srl?acao=notasorcamsexcel"
				onclick="exportaConsultaExcel(e)"><img
				src="resources/images/export_excel.png" title="Exportar para Excel"
				style="float: left; margin-right: 1%"></a>		
		</div>
		<div class="row"></div>
		<c:set var="vlrtotal" value="0"></c:set>
		
		<c:choose>
			<c:when test="${!empty reportnotas}">

				<table class="highlight responsive-table datables-table">
					<thead>				
						<tr>
							<th>Filial</th>
							<th>Cód.Cli</th>
							<th>Cliente</th>
							<th>Cidade</th>
							<th>Emissão</th>
							<th>Orçamento</th>							
							<th>NF</th>
							<th>Série</th>
							<th>TES</th>
							<th>Total</th>
							<th>Vendedor</th>
						</tr>
					</thead>
					
					<tbody class="table-font">
					
						<c:forEach var="orcam" items="${reportnotas}">
							
							<c:set var="vlrtotal" value="${vlrtotal + orcam.total}"></c:set>
							
							<tr>
								<td>${orcam.filial}</td>
								<td>${orcam.codigo}</td>
								<td>${orcam.nome}</td>
								<td>${orcam.municipio}</td>
								<td><fmt:formatDate
										value="${orcam.emissao}" pattern="dd/MM/yyyy" /></td>
								<td><a href="javascript:void(0)" class="modal-action modal-close"
									onclick="detalhenotaclick('${orcam.filial}', '${orcam.numero}','${orcam.codigo}','${orcam.nome}')"
									>${orcam.numero}</a></td>								
								<td>${orcam.nota}</td>
								<td>${orcam.serie}</td>
								<td>${orcam.tes}</td>
								<td><fmt:formatNumber
									value="${orcam.total}" type="number" pattern="#,#00.00#" />
								</td>
								<td>${orcam.vendedor}</td>
							</tr>
	
						</c:forEach>
					
					</tbody>
					<tfoot>	
						<tr>
							<td colspan="6">
							<fmt:formatNumber
									value="${fn:length(reportnotas)}" type="number" pattern="00" />							
								 itens
							</td>
							<td>Total</td>
							<td><fmt:formatNumber
									value="${vlrtotal}" type="number" pattern="#,#00.00#" />
							</td>
							<td colspan="3"></td>
						</tr>
					</tfoot>
				</table>

			</c:when>

			<c:otherwise>
				<h4>Sem orçamentos/notas nessa faixa de datas!</h4>
			</c:otherwise>
		</c:choose>
	</div>

</div>

<div class="modal" id="modaldetalhenota">
		<div class="center-align div-title-modal-detalhe">
			<span id="modalNotaLabel"></span>
		</div>		
		<div class="modal-content modaldivdetalhenota">
		</div>
		<div class="modal-footer">
			<a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Fechar</a>
		</div>					
</div>
