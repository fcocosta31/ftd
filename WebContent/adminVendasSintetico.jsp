<div class="center-align div-title-padrao col s12">
		Relatório de vendas sintético (xls)
</div>
<br><br>

<div class="container">
	<form id="formvendassintetico" action="srl" method="post">

			<div class="row">
				<div class="col l4 m4 s12">
					<label>Filial: </label> 
					<select	name="txtfilial" id="cmbEmpresas">
					</select>
				</div>
			</div>	
			<div class="row">			
				<div class="col l8 m8 s12">				
					<label>Cliente:</label>
					<br><br>
					<select id="cmbClientes" name="txtcliente"
					  class="select-with-search">
					</select>
				</div>
			</div>
			
			<br /><br />
			
			<div class="row">
				<div class="col l4 m4 s12">
					<label>Data inicial</label>
					<div>
						<input type="text" id="dataini" name="txtinicio" class="datepicker validate">				
					</div>
				</div>
				<div class="col l4 m4 s12">
					<label>Data final</label>
					<div>
						<input type="text" id="datafim" name="txtfim" class="datepicker validate">				
					</div>
				</div>			
			</div>
			<div class="row">			
				<div class="col l4 m4 s12">
					<label>TES Venda: </label> 
					<input placeholder="Ex.: 511"
						type="text" id="txtvd" name="tesvd" class="form-control" required
						size="3">
				</div>
				<div class="col l4 m4 s12">
					<label>TES Devol.: </label>
					 <input placeholder="Ex.: 021"
						type="text" id="txtdv" name="tesdv" class="form-control" required
						size="3">
				</div>
			</div>
			
			<br />
			<div class="row">
				<div class="col s12">
					<div class="input-group">
						<button class="btn waves-effect waves-light blue" type="submit">Enviar</button>						
					</div>
				</div>
			</div>
			
			 <input type="hidden" name="acao" value="vendassintetico">			
			
		
	</form>
</div>


<script type="text/javascript">

	$(document).ready(function(){
		loadComboEmpresasFTD();
		loadComboClientes();
		$('.select-with-search').select2({width: "100%"});
	});
		
	
	$(document).on("submit", "#formvendassintetico", function (e) {
		
	    e.preventDefault(); //otherwise a normal form submit would occur
	    fileDownload($(this));
	    
	});
	
</script>