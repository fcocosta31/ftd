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
		Relat�rio de visitas
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
						<p>Per�odo consultado: <fmt:formatDate value="${r.inicio}"
								pattern="dd/MM/yyyy" /> a <fmt:formatDate value="${r.fim}"
								pattern="dd/MM/yyyy" />
						</p>
						<p>Per�odo trabalhado: <fmt:formatDate value="${r.inireal}"
								pattern="dd/MM/yyyy" /> a <fmt:formatDate value="${r.fimreal}"
								pattern="dd/MM/yyyy" />
						</p> 							
						<p>Setor: ${r.usuario.setor}</p> <p>N�
							de escolas do setor: ${r.totalescolas}</p>

						<div style="background-color: #F7ECE8; font-size: 14pt; border: 1 solid #7B7674">
						<hr>
							<strong>Total de ${r.totalgeralvisitas} visitas efetuadas em ${r.qtdedias} dias</strong><br/>
							M�dia de visitas por dia:
							<fmt:formatNumber type="number" pattern="#" value="${r.totalgeralvisitas/r.qtdedias}"/>
							 escolas							
						<hr>
						</div>
						
						<p>Escolas visitadas no per�odo: ${r.totalvisitas} (<fmt:formatNumber type="number"
											pattern="00" value="${(r.totalvisitas/r.totalescolas)*100}" />%)</p>
						<p>Escolas com mais de uma visita no per�odo:
							${r.maisdeumavisita}</p> <p>Escolas n�o
							visitadas no per�odo: ${r.semvisitas} (<fmt:formatNumber type="number"
											pattern="00" value="${(r.semvisitas/r.totalescolas)*100}" />%)</p>
						<p>Escolas que nunca constaram em um roteiro de visitas: ${r.nuncavisitadas}</p>
						
						<hr>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
