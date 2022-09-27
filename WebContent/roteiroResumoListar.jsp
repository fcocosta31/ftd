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

		<form id="formresumoroteiro">

				<label>Vendedor: </label>
				<div class="row">
					<div class="col l6 m6 s12">
						<select name="txtsetor" id="cmbVendedor">
						</select>
					</div>
					<div class="col l6 m6 s12">
						<select name="cmbdependencia" id="cmbDependencia">
							<option value="privada">Privada</option>
							<option value="publica">Publica</option>
						</select>
					</div>									

                     <div class="col l6 m6 s12">
                         <label>Data inicial</label>
                         <div>
                             <input type="text" id="txtinicio" required name="txtdataini" class="datepicker validate">				
                         </div>
                     </div>
                     <div class="col l6 m6 s12">
                         <label>Data final</label>
                         <div>
                             <input type="text" id="txtfim" required name="txtdatafim" class="datepicker validate">					
                         </div>
                     </div>
				</div>
				<div class="row"></div>
				
				<input type="hidden" name="acao" value="resumoroteiro">
				<div class="row">
				<div class="col s12">				
	 				<button class="btn waves-effect waves-light blue" id="btnDialogResumo" type="button">
	                   	Enviar
	                </button>
                </div>
                </div>			
		</form>
	</div>

</div>