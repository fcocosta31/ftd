
<script type="text/javascript">
	$(document).ready(function(){
		loadComboEmpresasFTD();		
		loadComboClientes();
		$('.select-with-search').select2({width: "100%"});		
		$("#cmbGrpcli").selectpicker('refresh');
	});
    	
</script>
<div class="center-align div-title-padrao col s12">
		Relatório de vendas por produto (xls)
</div>
<br><br>

<div class="container">

	<form id="formvdprod" action="srl" method="post">

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
						
			<div class="row">
				<div class="col l4 m4 s12">
					<label>Data inicial</label>
					<div>
						<input type="text" id="dataini" name="dataini" class="datepicker validate">				
					</div>
				</div>
				<div class="col l4 m4 s12">
					<label>Data final</label>
					<div>
						<input type="text" id="datafim" name="datafim" class="datepicker validate">				
					</div>
				</div>			
			</div>

			<div class="row">
				<div class="col l4 m4 s12">
					<label>Grupo clientes:</label> 
					<select
						name="cmbgrpcli" id="cmbGrpcli" multiple title="Selecione o grupo...">
						<option value="05" selected>Livreiros</option>
						<option value="20" selected>Professores</option>
						<option value="30">Escolas Estaduais</option>
						<option value="40">Escolas Municipais</option>
						<option value="50" selected>Escolas Particulares</option>
						<option value="60">Prefeituras</option>
						<option value="97">Órgaos Governamentais</option>
						<option value="99" selected>Outros</option>
					</select>
				</div>
			</div>
			
			<div class="row">			
				<div class="col l4 m4 s12">
					<label>TES Venda: </label> 
					<input placeholder="Ex.: 511, 512"
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
			<div class="row">			
				<div class="col l4 m4 s12">
					<label>Série NF de: </label> 
					<input placeholder="Ex.: 005"
						type="text" id="txtserieini" name="txtserieini" class="form-control"
						size="3">
				</div>
				<div class="col l4 m4 s12">
					<label>Série NF até: </label>
					 <input placeholder="Ex.: 013"
						type="text" id="txtseriefim" name="txtseriefim" class="form-control"
						size="3">
				</div>
				<p>Deixar em branco caso queira todas as séries!</p>				
			</div>

			<input type="hidden" name="acao" value="vendasprodutos">
			
			<div class="row">			
				<div class="col s12">			
					<button class="btn waves-effect waves-light blue" type="submit">
						Enviar
					</button>
				</div>
			</div>

	</form>
</div>


<script type="text/javascript">

	$(document).on("submit", "#formvdprod", function (e) {
		
	    e.preventDefault(); //otherwise a normal form submit would occur
	    fileDownload($(this));
	    
	});
	
</script>