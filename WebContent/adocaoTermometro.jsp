<script type="text/javascript">
	$(document).ready(function(){
		vendedorselect();
		loadYearTermometro();
	});
	$(document).on('click','#btnDialogConfirmTermometro',function(){
		fileDownload($("formtermometro"));
	});
</script>

<div class="center-align div-title-padrao col s12">
		Termômetro das adoções
</div>
<br><br>

<div class="container">

	<div class="row" id="divoptserieescola">
		<form action="srl" method="post" id="formtermometro">
			
			<div class="col l4 m4 s12">
				<select name="txtsetor" id="cmbVendedor">
					<c:if test="${usuariologado.cargo lt 3}">
						<option value="0">Todos</option>
					</c:if>
				</select>
			</div>
			<div class="row"></div>			
			<div class="col l4 m4 s12">
				<select id="cmbAnoAdocao" name="txtano">
				</select>
			</div>
			<div class="row"></div>
			<div class="col s12">
				<h5>Tipo: </h5>				
				<p>
					<input type="radio"	class="with-gap" name="txttipo" id="rd1" value="sintetico" checked>
					<label for="rd1">Sintético</label>
					<input type="radio" class="with-gap" name="txttipo" id="rd2" value="analitico">
					<label for="rd2">Analitico</label>
				</p>
				<p>
					<input type="radio" class="with-gap" name="txttipo" id="rd3" value="monitorftd">
					<label for="rd3">Monitor FTD</label>
					<input type="radio" class="with-gap" name="txttipo" id="rd4" value="faturamento">
					<label for="rd4">Previsão de Faturamento</label>
				</p>				
			</div>
			<div class="row"></div>
			<div class="col s12">
				<div class="button-group">
					<input	type="hidden" name="acao" value="loadadocaotermometro">
					<button class="waves-effect waves-light btn blue" id="btnDialogConfirmTermometro">Enviar</button>
				</div>
			</div>
		</form>
	</div>
	
</div>


