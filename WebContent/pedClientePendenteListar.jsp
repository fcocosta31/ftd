<script type="text/javascript">
	$(document).ready(function(){
		loadComboClientes();
	});
</script>

<div class="center-align div-title-padrao col s12">
		Relatório de pedidos de clientes pendentes (xls)
</div>
<br><br>
<div class="container">

	<form action="srl" method="post" id="formpedclipendente">

		<div class="row">

			<div class="input-field col l12 m12 s12">
				<select id="cmbClientes" name="txtcliente">
				</select>
				<label>Cliente</label>
			</div>
			<div class="row"></div>														
			<br>			

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

			<div class="col l12 m12 s12">
				<h5>Tipo de relatório: </h5>
				
				<p>
					<input type="radio" name="txttipo" class="with-gap" id="rd1" value="detalhado" checked>
					<label for="rd1">Detalhado</label>

					<input type="radio" name="txttipo" class="with-gap" id="rd2" value="resumido">
					<label for="rd2">Resumido</label>
				</p>
				
			</div>
			<div class="col l12 m12 s12"><hr></div>			
			<div class="col l12 m12 s12">
				<button class="btn waves-effect waves-light blue download-loader" type="button">Enviar</button>
			</div>		
			
			<input type="hidden" value="itenspedclientependentes" name="acao">
			
		</div>
		
	</form>
</div>