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

	<div class="col-md-1 text-left noPrint" id="divmenulateral"></div>

	<div class="col-md-9">

		<div id="formtabela">


			<div class="modal">
				<!-- Place at bottom of page -->
			</div>


			<div class="row" id="divlistagemescolas">

				<h3>
					<strong>Roteiro de Divulgação</strong>
				</h3>
				<h5>Vendedor: ${roteiro.vendedor.nome}</h5>
				<h5>
					Data:
					<fmt:formatDate value="${roteiro.data}" pattern="dd/MM/yyyy" />
				</h5>
				<hr>

				<table class="table table-striped table-responsive tabela"
					id="tableescolas">
					<thead>
						<tr class="rows page-break" style="color: gray;">
							<th class="col-md-10">Descrição / Endereço / Fone /
								Classificação / Nº Alunos</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="t" items="${roteiro.escolas}">

							<tr class="rows">

								<td><strong style="font-size: 12pt; color: #000099;">
										${t.nome} <span>(<fmt:formatNumber type="number"
												pattern="0000" value="${t.id}" />)
									</span>
								</strong><br /> <input type="hidden" id="idescola" value="${t.id}">

									<span style="color: gray; font-size: 8pt;"><strong>Endereço
											Completo: </strong>${t.endereco}, ${t.bairro}, ${t.municipio} -
										${t.uf} / </span> <span style="color: gray; font-size: 8pt;"><strong>CEP:
									</strong>${t.cep}</span><br /> <span style="color: gray; font-size: 8pt;"><strong>Email:
									</strong>${t.email} / </span> <span style="color: gray; font-size: 8pt;"><strong>Fone:
									</strong>${t.telefone}</span><br /> <span style="color: gray; font-size: 8pt;"><strong>Classificação:
									</strong>${t.classificacao}</span> <span style="color: gray; font-size: 8pt;"><strong>
											/ Total alunos: </strong>${t.totalalunos}</span>
									<hr> <span style="color: gray; font-size: 9pt;"><strong>
											Alunos por série </strong></span><br /> <span
									style="color: gray; font-size: 8pt;"><strong>Berçário:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>Maternal:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>Mater_1:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>Mater_2:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>Mater_3:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>Infan_1:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>Infan_2:
									</strong>___</span><br /> <span style="color: gray; font-size: 8pt;"><strong>Infan_3:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>1
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>2
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>3
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>4
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>5
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>6
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>7
											Ano: </strong>___</span><br /> <span style="color: gray; font-size: 8pt;"><strong>8
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>9
											Ano: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>1
											Série: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>2
											Série: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>3
											Série: </strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>EJA:
									</strong>___&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
									style="color: gray; font-size: 8pt;"><strong>Supletivo:
									</strong>___</span>

									<hr>
									<hr>
									<hr>
									<hr></td>
							</tr>


						</c:forEach>

					</tbody>

					<tfoot>
						<tr class="rows" style="color: gray;">
							<td></td>
							<td class="noPrint"></td>
						</tr>
					</tfoot>

				</table>
			</div>

		</div>
		<!-- Fim da div formtabela -->

	</div>

	<div class="col-md-2"></div>

</div>
