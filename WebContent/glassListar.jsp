<script type="text/javascript">	
	
	$(document).ready(function(){		
		vendedorselect();
		loadComboYear();
	});

	$(document).on("click","#btnDialogGlass",function(event){
		event.preventDefault();
		sendGlassListar($("#formlistglass").serialize());
	});
	
</script>
<div class="center-align div-title-padrao col s12">
		Posição consolidada do consultor
</div>
<br><br>


<div class="container">

	<form method="post" action="srl" id="formlistglass">

			<div class="row">
				<div class="col l6 m6 s12">
					<label>Consultor: </label>
					<select name="txtsetor" id="cmbVendedor">
					</select>
				</div>
			</div>
			<div class="row">			
				<div class="col l6 m6 s12">
					<label>Dependência: </label>
					<select name="txtdependencia" id="cmbDependencia">
						<option value="privada">Privada</option>
						<option value="publica">Publica</option>
					</select>
				</div>
			</div>
			
			<div class="row">
				<div class="col l6 m6 s12">
					<label>Ano adoção: </label>
					<select name="txtyear" id="cmbano">
					</select>					
				</div>
			</div>
			<br/>			
			<div class="row">
				<div class="col l6 m6 s12">
					<div class="input-group">
						<input type="hidden" name="acao" value="getglassview"> 
						<button
							class="waves-effect waves-light btn blue noPrint" type="button" id="btnDialogGlass">
							Enviar
						</button>
					</div>
				</div>
			</div>

	</form>
</div>

