<script type="text/javascript">
	
		$(document).on('click', '#btnDialogResumo', function(e){
			var formulario = $("#formresumoroteiro").serialize();
			sendAjaxResumoRoteiro(formulario);
		});
		
		$(document).ready(function(){
			vendedorselect();
		});
		
	</script>

<div class="center-align div-title-padrao col s12">
		Relatório de visitas
</div>
<br><br>

<div class="container">

	<div class="row">


		<div class="col m8 s12 offset-m2">

			<div id="formtabela">


				<c:set var="r" value="${roteiroresumo}"></c:set>

				<div class="row"
					style="background-color: white;">
					<div class="row">
						<h3>${r.usuario.nome}</h3>
						<p>Período consultado: <fmt:formatDate value="${r.inicio}"
								pattern="dd/MM/yyyy" /> a <fmt:formatDate value="${r.fim}"
								pattern="dd/MM/yyyy" />
						</p>
						<p>Período trabalhado: <fmt:formatDate value="${r.inireal}"
								pattern="dd/MM/yyyy" /> a <fmt:formatDate value="${r.fimreal}"
								pattern="dd/MM/yyyy" />
						</p> 							
						<p>Setor: ${r.usuario.setor}</p> <p>Nº
							de escolas do setor: ${r.totalescolas}</p>

						<div style="background-color: #F7ECE8; font-size: 14pt; border: 1 solid #7B7674">
						<hr>
							<strong>Total de ${r.totalgeralvisitas} visitas efetuadas em ${r.qtdedias} dias</strong><br/>
							Média de visitas por dia:
							<fmt:formatNumber type="number" pattern="#" value="${r.totalgeralvisitas/r.qtdedias}"/>
							 escolas							
						<hr>
						</div>
						
						<p>Escolas visitadas no período: ${r.totalvisitas} (<fmt:formatNumber type="number"
											pattern="00" value="${(r.totalvisitas/r.totalescolas)*100}" />%)</p>
						<p>Escolas com mais de uma visita no período:
							${r.maisdeumavisita}</p> <p>Escolas não
							visitadas no período: ${r.semvisitas} (<fmt:formatNumber type="number"
											pattern="00" value="${(r.semvisitas/r.totalescolas)*100}" />%)</p>
						<p>Escolas que nunca constaram em um roteiro de visitas: ${r.nuncavisitadas}</p>
						
						<hr>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
