<div class="center-align div-title-padrao col s12">
		Exportar nota fiscal (csv)
</div>
<br><br>
<div class="container">
	<div class="row">
		<form class="form-horizontal" role="form" action="srl" method="post"
			id="formexportnf">
				<div class="col s12">
					<h5>Escolha o tipo de exportação</h5><br>
					<p>
					<input type="radio" name="tipoexport" class="with-gap" id="rd2" value="ftd" checked>
					<label for="rd2">Nota recebida do fornecedor</label>
					</p>
					<p>
					<input type="radio" name="tipoexport" class="with-gap" id="rd1" value="distribuidor">
					<label for="rd1">Nota emitida no Totvs (Local)</label>
					</p>
				
					<input type="hidden" name="acao" value="exportnotafiscal">
				</div>
				
				<div class="row"></div>
				<br><br><br>
				<div class="col s12" id="div-export-ftd">
					<div class="col l6 m6 s12">
						<label>Fornecedor:</label>
						<select name="fornecedor" id="cmbFornecedor"></select>
					</div>				
					<div class="col l6 m6 s12">
						<label>Número da nota</label>				
						<input type="number" placeholder="Somente números. Ex: 238938" name="txtnota">
					</div>				
				</div>
				<div class="col s12 noDisplay" id="div-export-distribuidor">
					<div class="col l6 m6 s12">
						<label>Filial: </label> 
						<select	name="filial" id="cmbEmpresas">
						</select>
					</div>
					<div class="col l4 m4 s12">
						<label>Nota Fiscal:</label>
						<input type="text" name="numeronotafiscal" placeholder="Somente números. Ex: 238938">
					</div>
					<div class="col l2 m2 s12">
						<label>Série:</label>
						<input type="text" name="serienotafiscal" placeholder="Ex: 005">
					</div>														
				</div>				
				<br><br><br>
				<div class="row"></div>
				<br>
				<div class="col s12">
					<button class="btn waves-effect waves-light blue download-loader" type="button">Exportar</button>
				</div>				
		</form>
	</div>
</div>

<script type="text/javascript">
	
	$('input[type=radio][name=tipoexport]').change(function(){
		if(this.value == 'distribuidor'){
			$('#div-export-distribuidor').removeClass('noDisplay');
			$('#div-export-ftd').addClass('noDisplay');
		}else{
			$('#div-export-ftd').removeClass('noDisplay');
			$('#div-export-distribuidor').addClass('noDisplay');			
		}
	});
	
	$(document).ready(function(){
		loadComboEmpresasFTD();
		loadComboFornecedor();
	});
</script>