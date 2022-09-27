<div class="center-align div-title-padrao col s12">
		Listagem de Orçamentos (Totvs)
</div>
<br><br>
<div class="container">
	<div class="row">
		<form action="srl" method="post">
		
		<div class="col l3 m3 s12">
			<label>Filial: </label> 
			<select	name="filial" id="cmbEmpresas">
			</select>
		</div>
		
		<div class="col l3 m3 s12">
			<label>Situação: </label>
			<select	name="aberto">
				<option value="sim" selected>Abertos</option>
				<option value="nao">Encerrados</option>
			</select>
		</div>

		<div class="col l3 m3 s12">
			<label>Cliente de: </label>
			<input type="text" placeholder="Ex.: 003909" name="clienteini">
		</div>

		<div class="col l3 m3 s12">
			<label>Cliente até: </label>
			<input type="text" placeholder="Ex.: 004020" name="clientefim">
		</div>
		
		<div class="col l6 m6 s12">
			<label>Data inicial</label>
			<div>
				<input type="text" id="dataini" name="dataini" class="datepicker validate">				
			</div>
		</div>
		<div class="col l6 m6 s12">
			<label>Data final</label>
			<div>
				<input type="text" id="datafim" name="datafim" class="datepicker validate">					
			</div>
		</div>
		
		<div class="col l6 m6 s12">
			<button class="btn waves-effect waves-light blue" type="submit">Enviar</button>
		</div>		
		
		<input type="hidden" name="acao" value="notasreport">
		
		</form>
	</div>
</div>

<script type="text/javascript">

	$(document).ready(function(){
		loadComboEmpresasFTD();
	});
		
</script>	