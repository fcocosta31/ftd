<script type="text/javascript">

	$(document).ready(function(){
		window.print();
	});
	
	window.onfocus=function(){
		window.close();
	};
	
</script>

<h3>
	<strong>Roteiro de Divulgação</strong>
</h3>
<h5>Vendedor: ${roteiro.vendedor.nome}</h5>
<h5>Nº Controle: <fmt:formatNumber type="number" pattern="0000" value="${roteiro.id}" />
	Data: <fmt:formatDate value="${roteiro.data}" pattern="dd/MM/yyyy" />
</h5>

<table class="page-break table table-striped table-responsive tabela"
	id="tableescolas">
	<thead>
		<tr class="rows" style="color: gray;">
			<th class="col s10"></th>
		</tr>
	</thead>

	<tbody>
		<c:forEach var="t" items="${roteiro.escolas}">

			<tr class="border_bottom">

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
							/ Total alunos: </strong>${t.totalalunos}</span><br/>
					<span style="color: gray; font-size: 8pt;"><strong>Obs.: </strong>${t.observacao}</span>											
					<hr> <img src="resources/images/roteiro_info.png"
					width="100%" height="auto"
					class="img-responsive wp-post-image center-align">
					<hr>
					<hr>
					<hr></td>
			</tr>


		</c:forEach>

	</tbody>

	<tfoot class="noPrint">
		<tr>
			<td></td>
		</tr>
	</tfoot>

</table>


