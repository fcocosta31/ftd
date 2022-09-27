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
	
	window.onfocus=function(){
		window.close();
	};
	
</script>



<div id="divlistagemescolas">

	<table>
		<thead>
			<tr class="border_bottom page-break" style="color: gray;">
				<th class="col s12"><strong>${fn:length(listaescolas)}
						escolas do vendedor: </strong> <c:out
						value="${listaescolas[0].vendedor.nome}" /></th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="t" items="${listaescolas}">

				<tr class="border_bottom">
					<td><strong>${t.nome}</strong><br /> <span
						style="color: gray; font-size: 8pt;"><strong>Endereço:
						</strong>${t.endereco}, ${t.complemento}, ${t.bairro},
							${t.municipio}-${t.uf}</span><span style="color: gray; font-size: 8pt;"><br />
							<strong>CEP: </strong>${t.cep}</span><span
						style="color: gray; font-size: 8pt;"><strong> /
								Telefone/E-mail: </strong>${t.telefone} / ${t.email}</span><span
						style="color: gray; font-size: 8pt;"><br /> <strong>Classificação:
						</strong>${t.classificacao}</span> <span style="color: gray; font-size: 8pt;"><strong>
								/ Aluno(s): ${t.totalalunos}</strong></span> <br /> <span
						style="color: gray; font-size: 8pt;"><strong>Ultimas
								visitas: </strong> <c:choose>
								<c:when test="${empty t.ultimavisita}">Nenhuma visita</c:when>
								<c:otherwise>
									<c:forEach var="visitas" items="${t.ultimavisita}">
																		[<fmt:formatDate value="${visitas}"
											pattern="dd/MM/yyyy" />]
																	</c:forEach>
																	 [Obs: ${t.observacao}]															
															</c:otherwise>
							</c:choose> </span></td>
				</tr>
			</c:forEach>

		</tbody>

		<tfoot>
			<tr class="border_bottom" style="color: gray;">
				<td colspan="3"></td>
			</tr>
		</tfoot>

	</table>
</div>
